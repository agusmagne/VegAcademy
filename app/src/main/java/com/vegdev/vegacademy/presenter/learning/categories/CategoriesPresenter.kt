package com.vegdev.vegacademy.presenter.learning.categories

import android.content.Context
import androidx.navigation.NavController
import com.vegdev.vegacademy.model.domain.interactor.learning.CategoriesInteractor
import com.vegdev.vegacademy.view.learning.categories.CategoriesFragmentDirections
import com.vegdev.vegacademy.view.learning.categories.CategoriesView
import com.vegdev.vegacademy.view.main.MainView

class CategoriesPresenter(
    val context: Context,
    val iCategoriesView: CategoriesView,
    val mainView: MainView,
    val navController: NavController,
    val interactor: CategoriesInteractor
) {

    suspend fun fetchAndBuildRecyclerViews() {
        mainView.showProgress()
        iCategoriesView.hideLayout()

        val videoCategories = interactor.getVideoCategories()
        iCategoriesView.buildVideosRV(CategoriesAdapter(videoCategories) {
            navController.navigate(CategoriesFragmentDirections.actionVideo(it))
        })

        val articlesCategories = interactor.getArticlesCategories()
        iCategoriesView.buildArticlesRV(CategoriesAdapter(articlesCategories) {
            navController.navigate(CategoriesFragmentDirections.actionVideo(it))
        })

        mainView.hideProgress()
        iCategoriesView.showLayout()
    }

}