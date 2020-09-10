package com.vegdev.vegacademy.presenter.recipes.info.stepsAdater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R

class InfoStepsAdapter(val steps: MutableList<String>) :
    RecyclerView.Adapter<InfoStepsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoStepsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.single_info_step, parent, false)
        return InfoStepsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfoStepsViewHolder, position: Int) {
        holder.bindStep(steps[position], position)
    }

    override fun getItemCount(): Int = steps.size
}