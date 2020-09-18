package com.vegdev.vegacademy.presenter.recipes.details.adapter.steps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R

class DetailsStepsAdapter(val steps: MutableList<String>) :
    RecyclerView.Adapter<InfoStepsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoStepsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_ingredients_step, parent, false)
        return InfoStepsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfoStepsViewHolder, position: Int) {
        holder.bindStep(steps[position], position)
    }

    override fun getItemCount(): Int = steps.size
}