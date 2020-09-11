package com.vegdev.vegacademy.presenter.recipes.recipes.adapter.single

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.storage.FirebaseStorage
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import kotlinx.android.synthetic.main.recipes_child_single.view.*

class SingleRecipeAdapter(
    options: FirestorePagingOptions<SingleRecipe>,
    val onRecipeClick: (SingleRecipe, Drawable, View) -> Unit
) : FirestorePagingAdapter<SingleRecipe, SingleRecipeViewHolder>(options) {

    private val storage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleRecipeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recipes_child_single, parent, false)
        return SingleRecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: SingleRecipeViewHolder,
        position: Int,
        model: SingleRecipe
    ) {
        holder.bindRecipe(model, storage)
        holder.itemView.src.transitionName = model.id
        holder.itemView.setOnClickListener {
            onRecipeClick(
                model,
                holder.itemView.src.drawable,
                holder.itemView.src
            )
        }
    }
}