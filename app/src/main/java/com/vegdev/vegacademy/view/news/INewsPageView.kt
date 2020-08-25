package com.vegdev.vegacademy.view.news

import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.presenter.news.pager.NewsViewHolder

interface INewsPageView {
    fun buildRv(adapter: RecyclerView.Adapter<NewsViewHolder>)
}