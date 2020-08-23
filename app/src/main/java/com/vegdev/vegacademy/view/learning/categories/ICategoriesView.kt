package com.vegdev.vegacademy.view.learning.categories

import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.presenter.learning.CategoryViewHolder
import com.vegdev.vegacademy.view.BaseView

interface ICategoriesView : BaseView {

    fun buildVideosRV(adapter: RecyclerView.Adapter<CategoryViewHolder>)
    fun buildArticlesRV(adapter: RecyclerView.Adapter<CategoryViewHolder>)

}