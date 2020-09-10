package com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.data.models.TypesRecipe

class ParentRecipesAdapter(
    private val types: TypesRecipe,
    private val onChildRecipeClick: (recipe: SingleRecipe, src: Drawable) -> Unit
) :
    RecyclerView.Adapter<ParentRecipesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecipesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipes_parent_single, parent, false)
        return ParentRecipesViewHolder(itemView) { recipe, drawable ->
            onChildRecipeClick(recipe, drawable)
        }
    }

    override fun onBindViewHolder(holder: ParentRecipesViewHolder, position: Int) {
        holder.bindView(types.types[position])
    }

    override fun getItemCount(): Int = types.types.size

}