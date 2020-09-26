package com.vegdev.vegacademy.contract.recipes.recipes

import android.graphics.Bitmap
import android.view.View
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.recipes.parent.adapter.ParentRecipesAdapter
import com.vegdev.vegacademy.presenter.recipes.recipes.parent.adapter.ScrollStateHolder

interface RecipesContract {

    interface View {

        fun startPostponedEnterTransition()
        fun buildRecipesParentRV(adapter: ParentRecipesAdapter)
        fun openRecipeDetails(recipe: SingleRecipe, bitmap: Bitmap?, view: android.view.View)
        fun makeToast(message: String)

    }

    interface Actions {
        suspend fun buildRVs(scrollStateHolder: ScrollStateHolder)
        fun openRecipeDetails(recipe: SingleRecipe, bitmap: Bitmap?, view: android.view.View)
    }

}