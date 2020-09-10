package com.vegdev.vegacademy.presenter.recipes.toprecipes.toprecipes

import android.content.Context
import androidx.fragment.app.Fragment
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.TopRecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.toprecipes.adapter.ParentRecipesAdapter
import com.vegdev.vegacademy.view.main.main.MainView
import com.vegdev.vegacademy.view.recipes.recipes.TopRecipesFragmentDirections
import com.vegdev.vegacademy.view.recipes.recipes.TopRecipesView

class TopRecipesPresenter(
    private val context: Context,
    private val fragment: Fragment,
    private val view: TopRecipesView,
    private val iMainView: MainView,
    private val interactor: TopRecipesInteractor
) {

    suspend fun buildRVs() {
        iMainView.showProgress()
        val allRecipeTypes = interactor.getAllRecipeTypes()!!
        val parentAdapter = ParentRecipesAdapter(allRecipeTypes) {
            val directions =
                TopRecipesFragmentDirections.actionNavigationToprecipesToNavigationRecipeInfo(it)
            iMainView.navigateToDirection(directions)
        }
        view.buildRecipesParentRV(parentAdapter)
        iMainView.hideProgress()
    }
}