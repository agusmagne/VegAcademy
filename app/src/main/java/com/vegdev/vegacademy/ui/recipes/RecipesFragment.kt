package com.vegdev.vegacademy.ui.recipes

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vegdev.vegacademy.IToolbar
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Filter
import com.vegdev.vegacademy.models.Recipe
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.fragment_recipes.*
import kotlinx.android.synthetic.main.fragment_recipes_element.view.*
import kotlinx.android.synthetic.main.recipe_single_filter.view.*
import java.util.*

class RecipesFragment : Fragment() {


    private lateinit var firestore: FirebaseFirestore
    private lateinit var rvAdapter: FirestorePagingAdapter<Recipe, RecipeViewHolder>
    private var iToolbar: IToolbar? = null
    private val layoutUtils = LayoutUtils()

    private val FILTER_TITLE = 0
    private val FILTER_TASTE = 1
    private val FILTER_MEAL = 2
    private var FILTER_BOTH = "Ambos"
    private var filtersList: MutableList<Filter?> = mutableListOf()
    private var filtersAdapter: FiltersStaggeredRvAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        firestore = FirebaseFirestore.getInstance()
        rvAdapter = fetchRecipes(firestore) { recipe ->
            findNavController().navigate(
                RecipesFragmentDirections.actionNavigationRecipesToNavigationRecipeInfo(
                    recipe
                )
            )
        }
        filtersAdapter = FiltersStaggeredRvAdapter(filtersList) {
            // on filter click
            filtersList.remove(it)
            filtersAdapter?.notifyDataSetChanged()
            fetchFilteredRecipes()
        }
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipes_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        filters_rv.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = filtersAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_addrecipe).isVisible = true
        menu.findItem(R.id.action_filterrecipe).isVisible = true
    }

    override fun onStart() {
        super.onStart()
        rvAdapter.startListening()
        iToolbar?.searchRecipesOn()
    }

    override fun onStop() {
        super.onStop()
        rvAdapter.stopListening()
        iToolbar?.searchRecipesOff()
    }

    override fun onResume() {
        super.onResume()
        fetchFilteredRecipes()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IToolbar) {
            iToolbar = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        iToolbar = null
    }

    private fun fetchFilteredRecipes() {
        val config = PagedList.Config.Builder().setInitialLoadSizeHint(5).setPageSize(3).build()
        val response =
            FirestorePagingOptions.Builder<Recipe>()
                .setQuery(getFilteredRecipesQuery(), config, Recipe::class.java)
                .build()
        rvAdapter.updateOptions(response)
    }

    fun fetchFilteredRecipes(byTitle: String) {
        val isSameFilterString = updateFilters(byTitle)

        if (!isSameFilterString) {
            val query = getFilteredRecipesQuery()
            query.addSnapshotListener { value, _ ->
                val recipes = value?.toObjects(Recipe::class.java)
                recipes?.let {
                    if (it.isEmpty()) {
                        no_recipes_found.visibility = View.VISIBLE
                    } else {
                        no_recipes_found.visibility = View.INVISIBLE
                    }
                }
            }
            val config = PagedList.Config.Builder().setInitialLoadSizeHint(5).setPageSize(3).build()
            val response =
                FirestorePagingOptions.Builder<Recipe>().setQuery(query, config, Recipe::class.java)
                    .build()
            rvAdapter.updateOptions(response)
        } else {
            layoutUtils.createToast(
                requireContext(),
                "Ya estás buscando recetas que contengan '$byTitle'"
            )
        }
    }

    fun fetchFilteredRecipes(byTaste: String, byMeal: String) {
        val didAnyFilterChange = updateFilters(byTaste, byMeal)

        if (didAnyFilterChange) {
            val query = getFilteredRecipesQuery()
            query.addSnapshotListener { value, _ ->
                val recipes = value?.toObjects(Recipe::class.java)
                recipes?.let {
                    if (it.isEmpty()) {
                        no_recipes_found.visibility = View.VISIBLE
                    } else {
                        no_recipes_found.visibility = View.INVISIBLE
                    }
                }
            }
            val config = PagedList.Config.Builder().setInitialLoadSizeHint(5).setPageSize(3).build()
            val response =
                FirestorePagingOptions.Builder<Recipe>().setQuery(query, config, Recipe::class.java)
                    .build()
            rvAdapter.updateOptions(response)
        } else {
            layoutUtils.createToast(requireContext(), "No has cambiado ningún filtro")
        }
    }

    private fun getFilteredRecipesQuery(): Query {
        var query: Query = firestore.collection("rec")
        val titleFilter = filtersList.filter { it?.type == FILTER_TITLE }
        val tasteFilter = filtersList.filter { it?.type == FILTER_TASTE }
        val mealFilter = filtersList.filter { it?.type == FILTER_MEAL }

        if (titleFilter.isNotEmpty()) {
            val filterString = titleFilter[0]!!.title
            query = query.whereGreaterThanOrEqualTo("title", filterString)
                .whereLessThan("title", getFilterTo(filterString))
        }

        if (tasteFilter.isNotEmpty()) {
            val filterString = tasteFilter[0]!!.title.toLowerCase(Locale.ROOT)
            query = query.whereEqualTo("taste", filterString)
        }

        if (mealFilter.isNotEmpty()) {
            val filterString = mealFilter[0]!!.title.toLowerCase(Locale.ROOT)
            query = query.whereEqualTo("meal", filterString)
        }
        return query
    }

    private fun updateFilters(byTitle: String): Boolean {
        // remove old filter by title
        val filterByTitle = filtersList.filter { it?.type == FILTER_TITLE }
        return if (filterByTitle.isNotEmpty()) {
            if (filterByTitle[0]?.title == byTitle) {
                true
            } else {
                filtersList.remove(filterByTitle[0])
                filtersList.add(Filter(byTitle, FILTER_TITLE))
                filtersAdapter?.notifyDataSetChanged()
                false
            }
        } else {
            filtersList.add(Filter(byTitle, FILTER_TITLE))
            filtersAdapter?.notifyDataSetChanged()
            false
        }
    }


    private fun updateFilters(byTaste: String, byMeal: String): Boolean {

        var didAnyFilterChange = false

        // erase old filters and update them with new ones
        val filterByTaste = filtersList.filter { it?.type == FILTER_TASTE }
        if (filterByTaste.isNotEmpty()) {
            if (filterByTaste[0]?.title != byTaste) {
                filtersList.remove(filterByTaste[0])
                didAnyFilterChange = true
                if (byTaste != FILTER_BOTH) {
                    filtersList.add(Filter(byTaste, FILTER_TASTE))
                }
            }
        } else {
            if (byTaste != FILTER_BOTH) {
                didAnyFilterChange = true
                filtersList.add(Filter(byTaste, FILTER_TASTE))
            }
        }

        val filterByMeal = filtersList.filter { it?.type == FILTER_MEAL }
        if (filterByMeal.isNotEmpty()) {
            if (filterByMeal[0]?.title != byMeal) {
                filtersList.remove(filterByMeal[0])
                didAnyFilterChange = true
                if (byMeal != FILTER_BOTH) {
                    filtersList.add(Filter(byMeal, FILTER_MEAL))
                }
            }
        } else {
            if (byMeal != FILTER_BOTH) {
                didAnyFilterChange = true
                filtersList.add(Filter(byMeal, FILTER_MEAL))
            }
        }
        filtersAdapter?.notifyDataSetChanged()
        return didAnyFilterChange
    }

    private fun getFilterTo(string: String): String {
        // when filtering from a string to a string, this method builds filterTo string
        val length = string.length
        val lastLetter = string.substring(length - 1, length)
        val stringWithoutLastLetter = string.toLowerCase(Locale.ROOT).substring(0, length - 1)
        return stringWithoutLastLetter + (lastLetter.toCharArray()[0] + 1).toString()
    }

    fun getFiltersList(): MutableList<Filter?> = filtersList

    private fun fetchRecipes(
        firestore: FirebaseFirestore,
        onRecipeClick: (Recipe) -> Unit
    ): FirestorePagingAdapter<Recipe, RecipeViewHolder> {
        val query = firestore.collection("rec")
        val config =
            PagedList.Config.Builder().setInitialLoadSizeHint(10).setPageSize(3).build()
        val response =
            FirestorePagingOptions.Builder<Recipe>().setQuery(query, config, Recipe::class.java)
                .build()

        return object : FirestorePagingAdapter<Recipe, RecipeViewHolder>(response) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecipeViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_recipes_element, parent, false)
                return RecipeViewHolder(itemView)
            }

            override fun onBindViewHolder(
                holder: RecipeViewHolder,
                position: Int,
                recipe: Recipe
            ) {
                holder.bindRecipe(recipe)
                holder.itemView.src.setOnClickListener { onRecipeClick(recipe) }
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                super.onLoadingStateChanged(state)
                when (state) {
                    LoadingState.ERROR -> layoutUtils.createToast(
                        requireContext(),
                        "Error al cargar más recetas"
                    )
                    LoadingState.FINISHED -> {
                        recipes_progressbar.visibility = View.INVISIBLE
                    }
                    LoadingState.LOADED -> {
                        recipes_progressbar.visibility = View.INVISIBLE
                    }
                    LoadingState.LOADING_INITIAL -> {
                    }
                    LoadingState.LOADING_MORE -> {
                        recipes_progressbar.visibility = View.VISIBLE
                    }
                }
            }
        }

    }
}

private class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindRecipe(recipe: Recipe) {
        Glide.with(itemView.context).load(recipe.src).into(itemView.src)
        val title = recipe.title!!
        val titleUppercase =
            title.substring(0, 1).toUpperCase(Locale.ROOT) + title.substring(1, title.length)
        itemView.title.text = titleUppercase
    }
}

private class FiltersStaggeredRvAdapter(
    val filtersList: MutableList<Filter?>,
    val listener: (Filter) -> Unit
) :
    RecyclerView.Adapter<FiltersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_single_filter, parent, false)
        return FiltersViewHolder(itemView)
    }

    override fun getItemCount(): Int = filtersList.size

    override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {
        filtersList[position]?.let { filter ->
            holder.bindFilter(filter)
            holder.itemView.setOnClickListener { listener(filter) }
        }
    }
}

private class FiltersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindFilter(filter: Filter) {
        itemView.filter_text.text = filter.title
    }
}