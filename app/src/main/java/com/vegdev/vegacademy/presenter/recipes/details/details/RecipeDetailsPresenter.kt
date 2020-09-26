package com.vegdev.vegacademy.presenter.recipes.details.details

import android.graphics.Bitmap
import com.vegdev.vegacademy.contract.recipes.details.RecipeDetailsContract
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.details.adapter.ingredients.DetailsIngredientsAdapter
import com.vegdev.vegacademy.presenter.recipes.details.adapter.steps.DetailsStepsAdapter
import com.vegdev.vegacademy.view.main.main.MainView

class RecipeDetailsPresenter(val view: RecipeDetailsContract.View, val iMainView: MainView) : RecipeDetailsContract.Actions {

    override fun buildRecyclerViewsAndBindRecipeInfo(recipe: SingleRecipe, src: Bitmap) {

        view.bindRecipe(recipe, src)

        val ingredientsAdapter = DetailsIngredientsAdapter(recipe.ing)
        view.setIngredientsRecyclerViewAdapter(ingredientsAdapter)

        val stepsAdapter = DetailsStepsAdapter(recipe.steps)
        view.setStepsRecyclerViewAdapter(stepsAdapter)

        view.startPostponedTransition(recipe.id)
    }
}