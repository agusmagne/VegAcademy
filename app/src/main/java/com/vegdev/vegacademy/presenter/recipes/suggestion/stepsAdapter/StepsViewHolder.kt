package com.vegdev.vegacademy.presenter.recipes.suggestion.stepsAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_step.view.*

class StepsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindStep(position: Int) {
        val n = (position + 1).toString()
        itemView.single_step_n.text = n
        itemView.single_step.hint = "Ingresa el paso" + " " + (position + 1).toString()
    }

}