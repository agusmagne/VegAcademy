package com.vegdev.vegacademy.view.recipes.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Recipe
import kotlinx.android.synthetic.main.fragment_recipes_element.view.*

class RecipesAdapter(
    options: FirestorePagingOptions<Recipe>,
    private val likedRecipesId: MutableList<String>?,
    val onRecipeClick: (Recipe) -> Unit,
    val onLikeBtnClick: (Boolean, String) -> Unit
) : FirestorePagingAdapter<Recipe, RecipesViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_recipes_element, parent, false)
        return RecipesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int, model: Recipe) {
        holder.bindRecipe(model)

        var isItLiked = false

        likedRecipesId?.let { likedRecipesId ->
            if (likedRecipesId.contains(model.id)) {
                isItLiked = true
                holder.colorRecipeAsLiked()
            }
        }

        holder.itemView.src.setOnClickListener {
            // ON RECIPE IMAGE CLICK


        }

        holder.itemView.likes_btn.setOnClickListener {
            // ON LIKE BTN CLICK
            it.isClickable = false

            val recipeId = model.id
            isItLiked = if (!isItLiked) {
                this.addLikeToView(holder.itemView.likes)
                holder.colorRecipeAsLiked()
                onLikeBtnClick(true, recipeId)
                likedRecipesId?.add(recipeId)
                true
            } else {
                this.substractLikeToView(holder.itemView.likes)
                holder.colorRecipeAsRegular()
                onLikeBtnClick(false, recipeId)
                likedRecipesId?.remove(recipeId)
                false
            }
            it.isClickable = true

        }
    }

    private fun addLikeToView(view: TextView) {
        view.text = (view.text.toString().toInt() + 1).toString()
    }

    private fun substractLikeToView(view: TextView) {
        view.text = (view.text.toString().toInt() - 1).toString()
    }

}