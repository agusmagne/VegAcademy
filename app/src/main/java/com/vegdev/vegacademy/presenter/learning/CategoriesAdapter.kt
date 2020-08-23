package com.vegdev.vegacademy.presenter.learning

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Category
import kotlinx.android.synthetic.main.fragment_learning_element.view.*

class CategoriesAdapter(
    private val videoCategories: MutableList<Category>,
    val clickListener: (Category) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_learning_element, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun getItemCount(): Int = videoCategories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = videoCategories[position]
        holder.bindCategory(category)
        holder.itemView.setOnClickListener { clickListener(category) }
    }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindCategory(category: Category) {
        itemView.title.text = category.title
        Glide.with(itemView.context).load(category.icon).into(itemView.src)
    }
}