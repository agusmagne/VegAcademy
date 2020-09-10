package com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R

class IngredientsAdapter(var size: Int) :
    RecyclerView.Adapter<IngredientsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_suggestion_ingredient, parent, false)
        return IngredientsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = size
}