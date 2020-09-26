package com.vegdev.vegacademy.presenter.news.pager

import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.contract.news.NewsContract
import com.vegdev.vegacademy.model.data.models.Category
import com.vegdev.vegacademy.model.domain.interactor.news.NewsPagerInteractor
import com.vegdev.vegacademy.view.news.news.NewsFragmentDirections

class NewsPagerPresenter(
    private val iNewsView: NewsContract.View,
    private val iNewsPagerView: NewsContract.PagerView,
    private val iMainView: MainContract.View
) : NewsContract.PagerActions {

    private val POSITION_VIDEOS = 0
    private val POSITION_ARTICLES = 1
    private val PATH_VIDEOS_CATEGORIES = "learning/videos/cat/"
    private val PATH_ARTICLES_CATEGORIES = "learning/art/cat/"
    private val interactor = NewsPagerInteractor()


    override suspend fun fetchNewsAndBuildRecyclerViews(position: Int) {
        if (position == POSITION_VIDEOS) {
            iNewsView.hideLayout()
            iMainView.showProgress()
            val newVideos = interactor.getNewVideos()
            iNewsPagerView.buildRv(NewsPagerRvAdapter(newVideos, {
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
            iNewsPagerView.buildRv(NewsPagerRvAdapter(newArticles, {
                //on element click
                    article ->
                val directions =
                    NewsFragmentDirections.actionNavigationNewsToNavigationWebview(article.link)
                iMainView.navigateToDirection(directions)
            }, {
                //on category click
                    article ->
                val path = PATH_ARTICLES_CATEGORIES + article.cat
                this.navigateToCategory(path)
            }))

            iMainView.hideProgress()
            iNewsView.showLayout()
        }
    }

    private fun navigateToCategory(path: String) {
        interactor.getElementCategory(path).addOnSuccessListener {
            val category = it.toObject(Category::class.java)!!
            val directions = NewsFragmentDirections.actionNavigationNewsToNavigationVideos(category)
            iMainView.navigateToDirection(directions)
        }
    }
}
