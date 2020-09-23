package com.vegdev.vegacademy.contract.recipes

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.recipes.parent.adapter.ParentRecipesAdapter

interface RecipesContract {

    interface View {

        fun startPostponedEnterTransition()
        fun buildRecipesParentRV(parentAdapter: ParentRecipesAdapter)
        fun openRecipeDetails(recipe: SingleRecipe, bitmap: Bitmap?, view: android.view.View)

    }

    interface Actions {

    }

}