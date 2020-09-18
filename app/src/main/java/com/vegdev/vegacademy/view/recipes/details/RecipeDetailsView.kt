package com.vegdev.vegacademy.view.recipes.details

import android.graphics.Bitmap
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.details.adapter.ingredients.DetailsIngredientsAdapter
import com.vegdev.vegacademy.presenter.recipes.details.adapter.steps.DetailsStepsAdapter

interface RecipeDetailsView {

    fun setIngredientsRecyclerViewAdapter(adapter: DetailsIngredientsAdapter)
    fun setStepsRecyclerViewAdapter(adapter: DetailsStepsAdapter)
    fun bindRecipe(recipe: SingleRecipe, src: Bitmap)
    fun startPostponedTransition(recipeId: String)

}