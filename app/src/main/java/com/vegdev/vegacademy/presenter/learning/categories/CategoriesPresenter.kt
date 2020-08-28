package com.vegdev.vegacademy.presenter.learning.categories

import android.content.Context
import androidx.navigation.NavController
import com.vegdev.vegacademy.model.domain.interactor.learning.CategoriesInteractor
import com.vegdev.vegacademy.view.learning.categories.CategoriesViewDirections
import com.vegdev.vegacademy.view.learning.categories.ICategoriesView
import com.vegdev.vegacademy.view.main.main.MainView

class CategoriesPresenter(
    val context: Context,
    val iCategoriesView: ICategoriesView,
    val mainView: MainView,
    val navController: NavController,
    val interactor: CategoriesInteractor
) {

    suspend fun fetchAndBuildRecyclerViews() {
        mainView.showProgress()
        iCategoriesView.hideLayout()

        val videoCategories = interactor.getVideoCategories()
        iCategoriesView.buildVideosRV(CategoriesAdapter(videoCategories) {
            navController.navigate(CategoriesViewDirections.actionVideo(it))
        })

        val articlesCategories = interactor.getArticlesCategories()
        iCategoriesView.buildArticlesRV(CategoriesAdapter(articlesCategories) {
            navController.navigate(CategoriesViewDirections.actionVideo(it))
        })

        mainView.hideProgress()
        iCategoriesView.showLayout()
    }

}