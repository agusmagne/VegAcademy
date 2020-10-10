package com.vegdev.vegacademy.view.recipes.details

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recipe_ingredients_step.view.*

class DetailsStepsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindStep(step: String, position: Int) {
        val n = (position + 1).toString()
        itemView.info_step_n.text = n
        itemView.info_step.text = step
    }
}