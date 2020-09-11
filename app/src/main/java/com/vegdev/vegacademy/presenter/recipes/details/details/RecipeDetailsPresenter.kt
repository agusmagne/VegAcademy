package com.vegdev.vegacademy.presenter.recipes.details.details

import android.graphics.Bitmap
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.details.adapter.ingredients.DetailsIngredientsAdapter
import com.vegdev.vegacademy.presenter.recipes.details.adapter.steps.DetailsStepsAdapter
import com.vegdev.vegacademy.view.main.main.MainView
import com.vegdev.vegacademy.view.recipes.details.RecipeDetailsView

class RecipeDetailsPresenter(val view: RecipeDetailsView, val iMainView: MainView) {

    fun buildRecyclerViewsAndBindRecipeInfo(recipe: SingleRecipe, src: Bitmap) {
        iMainView.showProgress()

        view.bindRecipe(recipe, src)

        val ingredientsAdapter = DetailsIngredientsAdapter(recipe.ing)
        view.setIngredientsRecyclerViewAdapter(ingredientsAdapter)

        val stepsAdapter = DetailsStepsAdapter(recipe.steps)
        view.setStepsRecyclerViewAdapter(stepsAdapter)

        iMainView.hideProgress()
        view.startPostponedTransition(recipe.id)
    }
}