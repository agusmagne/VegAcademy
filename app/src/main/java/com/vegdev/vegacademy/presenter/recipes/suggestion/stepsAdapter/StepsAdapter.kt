package com.vegdev.vegacademy.presenter.recipes.suggestion.stepsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R

class StepsAdapter(var size: Int) : RecyclerView.Adapter<StepsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.single_step, parent, false)
        return StepsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        holder.bindStep(position)
    }

    override fun getItemCount(): Int = size
}