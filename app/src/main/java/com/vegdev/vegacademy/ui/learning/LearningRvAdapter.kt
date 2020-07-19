package com.vegdev.vegacademy.ui.learning

import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import kotlinx.android.synthetic.main.fragment_learning_category_element.view.*

class LearningRvAdapter(
    val categories: TypedArray,
    val titles: TypedArray,
    val listener: (Int) -> Unit
) :
    RecyclerView
    .Adapter<LearningViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_learning_category_element, parent, false)

        return LearningViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categories.length()
    }

    override fun onBindViewHolder(holder: LearningViewHolder, position: Int) {

        holder.itemView.setOnClickListener { listener(position) }


        holder.bind(position, categories, titles)

    }

}

class LearningViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(
        position: Int,
        categories: TypedArray,
        titles: TypedArray
    ) {
        itemView.element_image_src.background = categories.getDrawable(position)
        itemView.element_title.text = titles.getText(position)
    }

}