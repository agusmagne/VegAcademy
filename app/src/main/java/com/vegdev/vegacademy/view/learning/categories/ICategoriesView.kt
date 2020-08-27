package com.vegdev.vegacademy.view.learning.categories

import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.presenter.learning.categories.CategoryViewHolder
import com.vegdev.vegacademy.view.IBaseView

interface ICategoriesView : IBaseView {

    fun buildVideosRV(adapter: RecyclerView.Adapter<CategoryViewHolder>)
    fun buildArticlesRV(adapter: RecyclerView.Adapter<CategoryViewHolder>)

}