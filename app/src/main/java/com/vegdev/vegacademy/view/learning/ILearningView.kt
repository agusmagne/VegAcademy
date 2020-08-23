package com.vegdev.vegacademy.view.learning

import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.presenter.learning.CategoryViewHolder

interface ILearningView {

    fun hideLayout()
    fun showLayout()
    fun buildVideosRV(adapter: RecyclerView.Adapter<CategoryViewHolder>)

}