package com.vegdev.vegacademy.view.news

import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.presenter.news.pager.NewsViewHolder

interface INewsPageView {
    fun buildRv(adapter: RecyclerView.Adapter<NewsViewHolder>)
    fun navigateTo(navDirections: NavDirections)
}