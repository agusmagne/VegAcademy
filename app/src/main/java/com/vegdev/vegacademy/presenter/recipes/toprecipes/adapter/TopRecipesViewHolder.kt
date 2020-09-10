package com.vegdev.vegacademy.presenter.recipes.toprecipes.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import kotlinx.android.synthetic.main.single_top_recipe.view.*

class TopRecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindRecipe(recipe: SingleRecipe, storage: FirebaseStorage) {
        storage.getReference("recipes/recipes/").child(recipe.id).downloadUrl.addOnSuccessListener {
            Glide.with(itemView.context).load(it).into(itemView.src)
        }.addOnFailureListener { }
        itemView.title.text = recipe.title
    }
}