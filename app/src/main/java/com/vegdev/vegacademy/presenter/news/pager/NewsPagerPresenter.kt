package com.vegdev.vegacademy.presenter.news.pager

import android.content.Context
import com.vegdev.vegacademy.model.data.models.Category
import com.vegdev.vegacademy.model.domain.interactor.news.NewsPagerInteractor
import com.vegdev.vegacademy.view.main.MainView
import com.vegdev.vegacademy.view.news.news.NewsFragmentDirections
import com.vegdev.vegacademy.view.news.pager.NewsPagerView

class NewsPagerPresenter(
    val context: Context,
    private val newsPagerView: NewsPagerView,
    private val mainView: MainView,
    private val interactor: NewsPagerInteractor
) {

    private val POSITION_VIDEOS = 0
    private val POSITION_ARTICLES = 1
    private val PATH_VIDEOS_CATEGORIES = "learning/videos/cat/"
    private val PATH_ARTICLES_CATEGORIES = "learning/art/cat/"

    suspend fun fetchNewsAndBuildRecyclerViews(position: Int) {
        if (position == POSITION_VIDEOS) {
            newsPagerView.hideLayout()
            mainView.showProgress()
            val newVideos = interactor.getNewVideos()
            newsPagerView.buildRv(NewsPagerRvAdapter(newVideos, {
                //on element click
                    video ->
                mainView.onVideoClicked(video.link)
            }, {
                //on category click
                    video ->
                mainView.showProgress()
                val path = PATH_VIDEOS_CATEGORIES + video.cat
                this.navigateToCategory(path)
            }))
        }

        if (position == POSITION_ARTICLES) {
            val newArticles = interactor.getNewArticles()
            newsPagerView.buildRv(NewsPagerRvAdapter(newArticles, {
                //on element click
                    article ->
                val directions =
                    NewsFragmentDirections.actionNavigationNewsToNavigationWebview(article.link)
                mainView.navigateToDirection(directions)
            }, {
                //on category click
                    article ->
                val path = PATH_ARTICLES_CATEGORIES + article.cat
                this.navigateToCategory(path)
            }))

            mainView.hideProgress()
            newsPagerView.showLayout()
        }
    }

    private fun navigateToCategory(path: String) {
        interactor.getElementCategory(path).addOnSuccessListener {
            val category = it.toObject(Category::class.java)!!
            val directions = NewsFragmentDirections.actionNavigationNewsToNavigationVideos(category)
            mainView.navigateToDirection(directions)
        }
    }
}
