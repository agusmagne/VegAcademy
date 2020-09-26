package com.vegdev.vegacademy.contract.learning

import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.presenter.learning.categories.CategoryViewHolder

interface CategoriesContract {

    interface View {
        fun buildVideosRV(adapter: RecyclerView.Adapter<CategoryViewHolder>?)
        fun buildArticlesRV(adapter: RecyclerView.Adapter<CategoryViewHolder>?)
    }

    interface Actions {
        suspend fun fetchAndBuildRecyclerViews()
    }
}