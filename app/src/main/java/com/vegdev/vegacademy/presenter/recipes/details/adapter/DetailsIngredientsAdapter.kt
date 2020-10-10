package com.vegdev.vegacademy.presenter.recipes.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.recipes.Ingredient
import com.vegdev.vegacademy.view.recipes.details.DetailsIngredientsViewHolder

class DetailsIngredientsAdapter(val ingredients: MutableList<Ingredient>) :
    RecyclerView.Adapter<DetailsIngredientsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsIngredientsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_details_ingredient, parent, false)
        return DetailsIngredientsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DetailsIngredientsViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bindIngredient(ingredient)
    }

    override fun getItemCount(): Int = ingredients.size
}