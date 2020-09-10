package com.vegdev.vegacademy.presenter.recipes.toprecipes

import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.TopRecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent.ParentRecipesAdapter
import com.vegdev.vegacademy.view.main.main.MainView
import com.vegdev.vegacademy.view.recipes.recipes.RecipesFragmentDirections
import com.vegdev.vegacademy.view.recipes.recipes.RecipesView

class RecipesPresenter(
    private val context: Context,
    private val fragment: Fragment,
    private val view: RecipesView,
    private val iMainView: MainView,
    private val interactor: TopRecipesInteractor
) {

    private var parentAdapter: ParentRecipesAdapter? = null

    suspend fun buildRVs() {
        iMainView.showProgress()
        if (parentAdapter == null) {
            val allRecipeTypes = interactor.getAllRecipeTypes()!!
            parentAdapter = ParentRecipesAdapter(allRecipeTypes) { recipe, drawable ->
                val directions =
                    RecipesFragmentDirections.actionNavigationToprecipesToNavigationRecipeInfo(
                        recipe,
                        drawable.toBitmap()
                    )
                iMainView.navigateToDirection(directions)
            }
        }
        view.buildRecipesParentRV(parentAdapter!!)
        iMainView.hideProgress()
    }
}