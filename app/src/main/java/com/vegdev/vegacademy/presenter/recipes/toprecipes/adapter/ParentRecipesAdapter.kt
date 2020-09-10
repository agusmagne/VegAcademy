package com.vegdev.vegacademy.presenter.recipes.toprecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.data.models.TypesRecipe

class ParentRecipesAdapter(
    private val types: TypesRecipe,
    private val onChildRecipeClick: (recipe: SingleRecipe) -> Unit
) :
    RecyclerView.Adapter<ParentRecipesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecipesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_parent_recipes, parent, false)
        return ParentRecipesViewHolder(itemView) {
            onChildRecipeClick(it)
        }
    }

    override fun onBindViewHolder(holder: ParentRecipesViewHolder, position: Int) {
        holder.bindView(types.types[position])
    }

    override fun getItemCount(): Int = types.types.size

}