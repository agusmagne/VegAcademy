package com.vegdev.vegacademy.ui.recipes

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.ObservableSnapshotArray
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.IToolbar
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Recipe
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.fragment_recipes.*
import kotlinx.android.synthetic.main.fragment_recipes_element.view.*
import java.util.*

class RecipesFragment : Fragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var rvAdapter: FirestoreRecyclerAdapter<Recipe, RecipeViewHolder>
    private var iToolbar: IToolbar? = null
    private var rvSnapshots: ObservableSnapshotArray<Recipe>? = null
    private val layoutUtils = LayoutUtils()

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
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipes_rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_addrecipe).isVisible = true
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

    fun searchRecipes(string: String) {
        // USE rvSnapshots
        val filteredRecipes = rvSnapshots?.filter { recipe ->
            recipe.title?.toLowerCase(Locale.ROOT)?.contains(string)!!
        }
        if (filteredRecipes != null) {
            recipes_rv.adapter = FilteredRecipesAdapter(filteredRecipes) { recipe ->
                findNavController().navigate(
                    RecipesFragmentDirections.actionNavigationRecipesToNavigationRecipeInfo(
                        recipe
                    )
                )
            }
        } else {
            layoutUtils.createToast(requireContext(), "No se encontraron recetas con ese nombre")
        }
    }

    private fun fetchRecipes(
        firestore: FirebaseFirestore,
        onRecipeClick: (Recipe) -> Unit
    ): FirestoreRecyclerAdapter<Recipe, RecipeViewHolder> {
        val query = firestore.collection("rec")
        val response =
            FirestoreRecyclerOptions.Builder<Recipe>().setQuery(query, Recipe::class.java).build()
        rvSnapshots = response.snapshots
        return object : FirestoreRecyclerAdapter<Recipe, RecipeViewHolder>(response) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_recipes_element, parent, false)
                return RecipeViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: RecipeViewHolder, position: Int, recipe: Recipe) {
                holder.bindRecipe(recipe)
                holder.itemView.src.setOnClickListener { onRecipeClick(recipe) }
            }
        }

    }
}

private class FilteredRecipesAdapter(
    val filteredRecipes: List<Recipe>,
    val onRecipeClick: (Recipe) -> Unit
) :
    RecyclerView.Adapter<RecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_recipes_element, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun getItemCount(): Int = filteredRecipes.size


    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = filteredRecipes[position]
        holder.bindRecipe(recipe)
        holder.itemView.src.setOnClickListener { onRecipeClick(recipe) }
    }

}

private class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindRecipe(recipe: Recipe) {
        Glide.with(itemView.context).load(recipe.src).into(itemView.src)
        itemView.title.text = recipe.title
    }
}