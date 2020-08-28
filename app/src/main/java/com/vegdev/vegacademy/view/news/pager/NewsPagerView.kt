package com.vegdev.vegacademy.view.news.pager

import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.presenter.news.pager.NewsViewHolder
import com.vegdev.vegacademy.view.IBaseView

interface NewsPagerView : IBaseView {
    fun buildRv(adapter: RecyclerView.Adapter<NewsViewHolder>)
}