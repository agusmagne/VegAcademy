package com.vegdev.vegacademy.contract

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent.ParentRecipesAdapter

interface RecipesContract {

    interface View {

        fun getSelectedRecipeDrawable(): Drawable?
        fun startPostponedEnterTransition()
        fun buildRecipesParentRV(parentAdapter: ParentRecipesAdapter)
        fun openRecipeDetails(recipe: SingleRecipe, bitmap: Bitmap?, view: android.view.View)

        interface SingleRecipeView {
            fun bindRecipe(title: String, likes: String, uri: Uri)
            fun checkRecipeAsLiked()
            fun uncheckRecipeAsLiked()
            fun getContext(): Context
            fun bindReturningRecipe(title: String, likes: String, bitmap: Bitmap?, iRecipeView: View)
        }

    }

}