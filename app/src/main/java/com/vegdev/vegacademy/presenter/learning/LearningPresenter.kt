package com.vegdev.vegacademy.presenter.learning

import android.content.Context
import androidx.navigation.NavController
import com.vegdev.vegacademy.IMainView
import com.vegdev.vegacademy.domain.interactor.learning.LearningInteractor
import com.vegdev.vegacademy.ui.learning.ILearningView
import com.vegdev.vegacademy.ui.learning.LearningFragmentDirections

class LearningPresenter(
    val context: Context,
    val iLearningView: ILearningView,
    val iMainView: IMainView,
    val navController: NavController,
    val interactor: LearningInteractor
) {

    suspend fun getVideoCategoriesAdapter() {
        iMainView.showprogress()
        iLearningView.hideLayout()

        val categoriesList = interactor.getVideoCategories()
        iLearningView.buildVideosRV(LearningFragmentAdapters(categoriesList) {
            navController.navigate(LearningFragmentDirections.actionVideo(it))
        })

        iMainView.hideprogress()
        iLearningView.showLayout()
    }

}