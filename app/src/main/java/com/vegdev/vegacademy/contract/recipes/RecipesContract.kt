package com.vegdev.vegacademy.contract.recipes

import android.graphics.Bitmap
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.recipes.parent.adapter.ParentRecipesAdapter

interface RecipesContract {

    interface View {

        fun startPostponedEnterTransition()
        fun buildRecipesParentRV(parentAdapter: ParentRecipesAdapter)
        fun openRecipeDetails(recipe: SingleRecipe, bitmap: Bitmap?, view: android.view.View)
        fun makeToast(message: String)

    }

    interface Actions {

    }

}