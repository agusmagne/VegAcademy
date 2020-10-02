package com.vegdev.vegacademy.contract.recipes.recipes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.request.RequestListener
import com.vegdev.vegacademy.model.data.models.recipes.SingleRecipe

interface SingleRecipeContract {

    interface View {
        fun setUriImageSource(
            uri: Uri,
            onBitmapReady: RequestListener<Drawable?>
        )

        fun setLikesButtonColors(isAlreadyLiked: Boolean)
        fun uncheckRecipeAsLiked()
        fun getContext(): Context
        fun setBitmapImageSource(
            bitmap: Bitmap?,
            onBitmapReady: RequestListener<Drawable?>
        )

        fun setRecipeClickable(boolean: Boolean)
        fun isLikeButtonClickable(boolean: Boolean)
        fun bindText(title: String, likes: String)
        fun addOrSubtractLikeFromButton(shouldAddLike: Boolean)
    }

    interface Actions {
        fun handleSingleRecipeLoading(recipe: SingleRecipe, iHolder: View)
        fun onSingleRecipeClick(recipe: SingleRecipe, drawable: Drawable, view: android.view.View)
        fun onLikesButtonClick(recipe: SingleRecipe, iHolder: View)
    }

}