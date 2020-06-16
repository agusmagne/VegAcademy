package com.vegdev.vegacademy.ui.learning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R

class LearningRvAdapter : RecyclerView.Adapter<LearningViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_element, parent, false)

        return LearningViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: LearningViewHolder, position: Int) {
    }

}

class LearningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}