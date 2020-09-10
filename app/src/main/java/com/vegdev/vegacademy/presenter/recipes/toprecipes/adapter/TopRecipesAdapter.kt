package com.vegdev.vegacademy.presenter.recipes.toprecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.storage.FirebaseStorage
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.SingleRecipe

class TopRecipesAdapter(
    options: FirestorePagingOptions<SingleRecipe>,
    val onRecipeClick: (SingleRecipe) -> Unit
) : FirestorePagingAdapter<SingleRecipe, TopRecipesViewHolder>(options) {

    private val storage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRecipesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.single_top_recipe, parent, false)
        return TopRecipesViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: TopRecipesViewHolder,
        position: Int,
        model: SingleRecipe
    ) {
        holder.bindRecipe(model, storage)
        holder.itemView.setOnClickListener { onRecipeClick(model) }
    }
}