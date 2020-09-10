package com.vegdev.vegacademy.presenter.recipes.info.ingredientsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Ingredient

class InfoIngredientsAdapter(val ingredients: MutableList<Ingredient>) :
    RecyclerView.Adapter<InfoIngredientsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoIngredientsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_info_ingredient, parent, false)
        return InfoIngredientsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfoIngredientsViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bindIngredient(ingredient)
    }

    override fun getItemCount(): Int = ingredients.size
}