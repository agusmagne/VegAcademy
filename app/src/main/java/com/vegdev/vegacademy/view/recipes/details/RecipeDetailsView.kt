package com.vegdev.vegacademy.view.recipes.details

import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.info.ingredientsAdapter.InfoIngredientsAdapter
import com.vegdev.vegacademy.presenter.recipes.info.stepsAdater.InfoStepsAdapter

interface RecipeDetailsView {

    fun setIngredientsRecyclerViewAdapter(adapter: InfoIngredientsAdapter)
    fun setStepsRecyclerViewAdapter(adapter: InfoStepsAdapter)
    fun bindRecipe(recipe: SingleRecipe)

}