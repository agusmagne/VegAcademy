package com.vegdev.vegacademy.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.models.Recipe
import kotlinx.android.synthetic.main.fragment_recipes.*
import kotlinx.android.synthetic.main.fragment_recipes_element.view.*

class RecipesFragment : Fragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var rvAdapter: FirestoreRecyclerAdapter<Recipe, RecipeViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    override fun onStart() {
        super.onStart()
        rvAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        rvAdapter.stopListening()
    }

    private fun fetchRecipes(
        firestore: FirebaseFirestore,
        onRecipeClick: (Recipe) -> Unit
    ): FirestoreRecyclerAdapter<Recipe, RecipeViewHolder> {
        val query = firestore.collection("rec")
        val response =
            FirestoreRecyclerOptions.Builder<Recipe>().setQuery(query, Recipe::class.java).build()
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

private class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindRecipe(recipe: Recipe) {
        Glide.with(itemView.context).load(recipe.src).into(itemView.src)
        itemView.title.text = recipe.title
    }

}