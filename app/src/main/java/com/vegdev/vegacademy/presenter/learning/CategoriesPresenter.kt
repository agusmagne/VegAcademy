package com.vegdev.vegacademy.presenter.learning

import android.content.Context
import androidx.navigation.NavController
import com.vegdev.vegacademy.IMainView
import com.vegdev.vegacademy.model.domain.interactor.learning.CategoriesInteractor
import com.vegdev.vegacademy.view.learning.categories.CategoriesViewDirections
import com.vegdev.vegacademy.view.learning.categories.ICategoriesView

class CategoriesPresenter(
    val context: Context,
    val iCategoriesView: ICategoriesView,
    val iMainView: IMainView,
    val navController: NavController,
    val interactor: CategoriesInteractor
) {

    suspend fun fetchAndBuildRecyclerViews() {
        iMainView.showprogress()
        iCategoriesView.hideLayout()

        val videoCategories = interactor.getVideoCategories()
        iCategoriesView.buildVideosRV(CategoriesAdapter(videoCategories) {
            navController.navigate(CategoriesViewDirections.actionVideo(it))
        })

        val articlesCategories = interactor.getArticlesCategories()
        iCategoriesView.buildArticlesRV(CategoriesAdapter(articlesCategories) {
            navController.navigate(CategoriesViewDirections.actionVideo(it))
        })

        iMainView.hideprogress()
        iCategoriesView.showLayout()
    }

}