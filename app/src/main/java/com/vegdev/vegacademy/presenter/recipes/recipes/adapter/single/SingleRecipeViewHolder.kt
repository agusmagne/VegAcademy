package com.vegdev.vegacademy.presenter.recipes.recipes.adapter.single

import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.FirebaseStorage
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import kotlinx.android.synthetic.main.recipes_child_single.view.*

class SingleRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindRecipe(recipe: SingleRecipe, storage: FirebaseStorage) {
        storage.getReference("recipes/recipes/").child(recipe.id).downloadUrl.addOnSuccessListener {
            Glide.with(itemView.context).load(it).into(itemView.src)
        }.addOnFailureListener { }
        itemView.title.text = recipe.title
    }

    fun bindReturningRecipe(
        recipe: SingleRecipe,
        storage: FirebaseStorage,
        onReturnedRecipeImageLoaded: () -> Unit
    ) {
        storage.getReference("recipes/recipes/").child(recipe.id).downloadUrl.addOnSuccessListener {
            Glide.with(itemView.context).load(it).listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onReturnedRecipeImageLoaded()
                    return false
                }
            }).into(itemView.src)
        }.addOnFailureListener { }
        itemView.title.text = recipe.title
    }
}