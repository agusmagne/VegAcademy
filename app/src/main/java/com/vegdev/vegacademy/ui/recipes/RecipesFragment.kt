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
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vegdev.vegacademy.ILayoutManager
import com.vegdev.vegacademy.IUserManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Filter
import com.vegdev.vegacademy.models.Recipe
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.fragment_recipes.*
import java.util.*

class RecipesFragment : Fragment() {


    private lateinit var firestore: FirebaseFirestore
    private var firebaseUser: FirebaseUser? = null
    private lateinit var rvAdapter: FirestorePagingAdapter<Recipe, RecipesFragmentAdapters.RecipeViewHolder>
    private var iLayoutManager: ILayoutManager? = null
    private val layoutUtils = LayoutUtils()
    private var likedRecipesId: MutableList<String> = mutableListOf()
    private var iUserManager: IUserManager? = null
    private var isLayoutLoaded: Boolean = false

    private val FILTER_TITLE = 0
    private val FILTER_TASTE = 1
    private val FILTER_MEAL = 2
    private var FILTER_BOTH = "Ambos"
    private var filtersList: MutableList<Filter?> = mutableListOf()
    private var filtersAdapter: RecipesFragmentAdapters.FiltersStaggeredRvAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        iLayoutManager?.currentlyLoading()
        firestore = FirebaseFirestore.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser
        likedRecipesId = iUserManager?.getCurrentUser()!!.likedRecipesId
        filtersAdapter = RecipesFragmentAdapters.FiltersStaggeredRvAdapter(filtersList) {
            // on filter click
            filtersList.remove(it)
            filtersAdapter?.notifyDataSetChanged()
            fetchFilteredRecipes()
        }
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAdapter = RecipesFragmentAdapters().fetchRecipes(
            firestore,
            likedRecipesId,
            firebaseUser!!.uid
        ) { recipe ->
            findNavController().navigate(
                RecipesFragmentDirections.actionNavigationRecipesToNavigationRecipeInfo(
                    recipe
                )
            )
        }
        recipes_rv.apply {
            layoutManager = object : LinearLayoutManager(context) {
                override fun onLayoutCompleted(state: RecyclerView.State?) {
                    super.onLayoutCompleted(state)
                    if (!isLayoutLoaded) {
                        iLayoutManager?.finishedLoading()
                        recipes_fragment.visibility = View.VISIBLE
                    }
                }
            }
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
        iLayoutManager?.searchRecipesOn()
    }

    override fun onStop() {
        super.onStop()
        rvAdapter.stopListening()
        iLayoutManager?.searchRecipesOff()
    }

    override fun onResume() {
        super.onResume()
        fetchFilteredRecipes()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ILayoutManager) iLayoutManager = context
        if (context is IUserManager) iUserManager = context
    }

    override fun onDetach() {
        super.onDetach()
        iLayoutManager = null
        iUserManager = null
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


}