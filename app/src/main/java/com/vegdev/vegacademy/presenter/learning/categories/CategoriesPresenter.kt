package com.vegdev.vegacademy.presenter.learning.categories

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Category
import com.vegdev.vegacademy.model.domain.interactor.learning.CategoriesInteractor
import com.vegdev.vegacademy.view.learning.categories.CategoriesView
import com.vegdev.vegacademy.view.main.main.MainView

class CategoriesPresenter(
    private val context: Context,
    private val view: CategoriesView,
    private val iMainView: MainView,
    private val navController: NavController,
    private val interactor: CategoriesInteractor
) {

    var videosAdapter: CategoriesAdapter? = null
    private var articlesAdapter: CategoriesAdapter? = null

    suspend fun fetchAndBuildRecyclerViews() {
        iMainView.showProgress()

        if (videosAdapter == null) {
            val videoCategories = interactor.getVideoCategories()
            videosAdapter = buildAdapter(videoCategories)
        }

        if (articlesAdapter == null) {
            val articlesCategories = interactor.getArticlesCategories()
            articlesAdapter = buildAdapter(articlesCategories)

        }
        view.buildVideosRV(videosAdapter!!)
        view.buildArticlesRV(articlesAdapter!!)
        iMainView.hideProgress()

    }

    private fun buildAdapter(categories: MutableList<Category>): CategoriesAdapter {
        return CategoriesAdapter(categories) { category, view ->
            val bundle = Bundle()
            bundle.putParcelable("category", category)
            val extras = FragmentNavigatorExtras(
                view[0] to (category.title + "src"),
                view[1] to (category.title + "back")
            )
            navController.navigate(R.id.navigation_videos, bundle, null, extras)
        }
    }

}