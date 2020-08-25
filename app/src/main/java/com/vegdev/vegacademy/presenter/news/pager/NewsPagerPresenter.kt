package com.vegdev.vegacademy.presenter.news.pager

import android.content.Context
import com.vegdev.vegacademy.model.data.repositories.news.NewsRepositoryImpl
import com.vegdev.vegacademy.view.main.IMainView
import com.vegdev.vegacademy.view.news.INewsPageView
import com.vegdev.vegacademy.view.news.INewsView

class NewsPagerPresenter(
    val context: Context,
    private val iNewsPageView: INewsPageView,
    val iNewsView: INewsView,
    val iMainView: IMainView
) {

    private val repository = NewsRepositoryImpl()

    suspend fun fetchNewsAndBuildRecyclerViews(position: Int) {
        if (position == 0) {
            iNewsView.hideLayout()
            iMainView.showprogress()

            val newVideos = repository.getNewVideos()
            iNewsPageView.buildRv(NewsPagerRvAdapter(newVideos, {}, {}))
        }

        if (position == 1) {
            val newArticles = repository.getNewArticles()
            iNewsPageView.buildRv(NewsPagerRvAdapter(newArticles, {}, {}))

            iMainView.hideprogress()
            iNewsView.showLayout()
        }
    }
}
