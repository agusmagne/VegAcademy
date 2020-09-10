package com.vegdev.vegacademy.presenter.recipes.info.info

import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.info.ingredientsAdapter.InfoIngredientsAdapter
import com.vegdev.vegacademy.presenter.recipes.info.stepsAdater.InfoStepsAdapter
import com.vegdev.vegacademy.view.main.main.MainView
import com.vegdev.vegacademy.view.recipes.details.RecipeDetailsView

class RecipesInfoPresenter(val view: RecipeDetailsView, val iMainView: MainView) {

    fun buildRecyclerViewsAndBindRecipeInfo(recipe: SingleRecipe) {
        iMainView.showProgress()

        view.bindRecipe(recipe)

        val ingredientsAdapter = InfoIngredientsAdapter(recipe.ing)
        view.setIngredientsRecyclerViewAdapter(ingredientsAdapter)

        val stepsAdapter = InfoStepsAdapter(recipe.steps)
        view.setStepsRecyclerViewAdapter(stepsAdapter)

        iMainView.hideProgress()
    }
}