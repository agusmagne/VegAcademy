package com.vegdev.vegacademy.presenter.learning.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Category
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.categories_single.view.*

class CategoriesAdapter(
    private val videoCategories: MutableList<Category>,
    private val clickListener: (Category, List<View>) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.categories_single, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun getItemCount(): Int = videoCategories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = videoCategories[position]
        holder.bindCategory(category)
        holder.itemView.src.transitionName = category.title + "src"
        holder.itemView.title.transitionName = category.title + "title"
        holder.itemView.transitionName = category.title + "back"
        holder.itemView.setOnTouchListener(LayoutUtils().getResizerOnTouchListener(holder.itemView))
        holder.itemView.setOnClickListener {
            clickListener(
                category,
                listOf(holder.itemView.src, holder.itemView.title, holder.itemView)
            )
        }
    }
}

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindCategory(category: Category) {
        itemView.title.text = category.title
        Glide.with(itemView.context).load(category.icon).into(itemView.src)
    }


}