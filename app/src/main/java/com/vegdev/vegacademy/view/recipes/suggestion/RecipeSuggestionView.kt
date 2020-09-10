package com.vegdev.vegacademy.view.recipes.suggestion

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ArrayAdapter
import com.vegdev.vegacademy.presenter.recipes.suggestion.ingredientsAdapter.IngredientsAdapter
import com.vegdev.vegacademy.presenter.recipes.suggestion.stepsAdapter.StepsAdapter

interface RecipeSuggestionView {
    fun buildIngredientsRecyclerView(adapter: IngredientsAdapter)
    fun getIngredientAtPosition(position: Int): View?
    fun buildStepsRecyclerView(adapter: StepsAdapter)
    fun getStepAtPosition(position: Int): View?
    fun setRecipeImageBitmap(bitmap: Bitmap)
    fun getRecipeImage(): Bitmap
    fun setRecipeImageUri(uri: Uri)
    fun buildSpinner(adapter: ArrayAdapter<String>)
    fun setPadding(padding: Int)
    fun getRecipeTypesSpinnerSelectedItem(): String
}