package com.vegdev.vegacademy.view.recipes.filters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Filter
import kotlinx.android.synthetic.main.recipe_single_filter.view.*

class FiltersAdapter(val filters: MutableList<Filter>, val onFilterClick: (Filter) -> Unit) :
    RecyclerView.Adapter<FiltersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_single_filter, parent, false)
        return FiltersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {
        filters[position].let { filter ->
            holder.bindFilter(filter)
            holder.itemView.setOnClickListener { onFilterClick(filter) }
        }
    }

    override fun getItemCount(): Int = filters.size
}

class FiltersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindFilter(filter: Filter) {
        itemView.filter_text.text = filter.title
    }
}