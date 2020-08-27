package com.vegdev.vegacademy.presenter.news.pager

import android.content.Context
import com.vegdev.vegacademy.model.data.models.Category
import com.vegdev.vegacademy.model.domain.interactor.news.NewsPagerInteractor
import com.vegdev.vegacademy.view.main.IMainView
import com.vegdev.vegacademy.view.news.news.NewsViewDirections
import com.vegdev.vegacademy.view.news.pager.INewsPageView

class NewsPagerPresenter(
    val context: Context,
    private val iNewsPageView: INewsPageView,
    private val iMainView: IMainView,
    private val interactor: NewsPagerInteractor
) {

    private val POSITION_VIDEOS = 0
    private val POSITION_ARTICLES = 1
    private val PATH_VIDEOS_CATEGORIES = "learning/videos/cat/"
    private val PATH_ARTICLES_CATEGORIES = "learning/art/cat/"

    suspend fun fetchNewsAndBuildRecyclerViews(position: Int) {
        if (position == POSITION_VIDEOS) {
            iNewsPageView.hideLayout()
            iMainView.showProgress()
            val newVideos = interactor.getNewVideos()
            iNewsPageView.buildRv(NewsPagerRvAdapter(newVideos, {
                //on element click
                    video ->
                iMainView.onVideoClicked(video.link)
            }, {
                //on category click
                    video ->
                iMainView.showProgress()
                val path = PATH_VIDEOS_CATEGORIES + video.cat
                this.navigateToCategory(path)
            }))
        }

        if (position == POSITION_ARTICLES) {
            val newArticles = interactor.getNewArticles()
            iNewsPageView.buildRv(NewsPagerRvAdapter(newArticles, {
                //on element click
                    article ->
                val directions =
                    NewsViewDirections.actionNavigationNewsToNavigationWebview(article.link)
                iMainView.navigateToDirection(directions)
            }, {
                //on category click
                    article ->
                val path = PATH_ARTICLES_CATEGORIES + article.cat
                this.navigateToCategory(path)
            }))

            iMainView.hideProgress()
            iNewsPageView.showLayout()
        }
    }

    private fun navigateToCategory(path: String) {
        interactor.getElementCategory(path).addOnSuccessListener {
            val category = it.toObject(Category::class.java)!!
            val directions = NewsViewDirections.actionNavigationNewsToNavigationVideos(category)
            iMainView.navigateToDirection(directions)
        }
    }
}
