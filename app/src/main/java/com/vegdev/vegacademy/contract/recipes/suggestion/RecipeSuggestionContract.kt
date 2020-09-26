package com.vegdev.vegacademy.contract.recipes.suggestion

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ArrayAdapter
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.ingredients.IngredientsAdapter
import com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.steps.StepsAdapter

interface RecipeSuggestionContract {

    interface View {
        fun buildIngredientsRecyclerView(adapter: IngredientsAdapter)
        fun getIngredientAtPosition(position: Int): android.view.View?
        fun buildStepsRecyclerView(adapter: StepsAdapter)
        fun getStepAtPosition(position: Int): android.view.View?
        fun setRecipeImageBitmap(bitmap: Bitmap)
        fun getRecipeImage(): Bitmap
        fun setRecipeImageUri(uri: Uri)
        fun buildSpinner(adapter: ArrayAdapter<String>)
        fun setPadding(padding: Int)
        fun getRecipeTypesSpinnerSelectedItem(): String
        fun makeButtonUnclickable()
        fun recipeSentConfirmation()
    }

    interface Actions {
        fun suggestRecipe(recipe: SingleRecipe)
        fun buildIngredientsRecyclerView()
        fun addIngredient()
        fun buildStepsRecyclerView()
        fun addStep()
        fun getImage()
        fun onPictureTaken(requestCode: Int, resultCode: Int, data: Intent?)
        fun buildSpinner()
    }

}