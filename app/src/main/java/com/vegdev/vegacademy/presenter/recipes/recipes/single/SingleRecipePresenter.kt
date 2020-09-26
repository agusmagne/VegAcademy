package com.vegdev.vegacademy.presenter.recipes.recipes.single

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.storage.FirebaseStorage
import com.vegdev.vegacademy.contract.recipes.recipes.RecipesContract
import com.vegdev.vegacademy.contract.recipes.recipes.SingleRecipeContract
import com.vegdev.vegacademy.model.data.dataholders.UserDataHolder
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.RecipesInteractor

class SingleRecipePresenter(
    private val iRecipesView: RecipesContract.View
) : SingleRecipeContract.Actions {

    private val firebaseStorage = FirebaseStorage.getInstance()
    private var selectedBitmap: Bitmap? = null
    private var selectedRecipe: SingleRecipe? = null
    private val interactor = RecipesInteractor()
    private val likedRecipes = UserDataHolder.likedRecipesId

    override fun handleSingleRecipeLoading(
        recipe: SingleRecipe,
        iHolder: SingleRecipeContract.View
    ) {
        // check if recipe is already liked and set colors accordingly
        iHolder.setLikesButtonColors(likedRecipes.contains(recipe.id))

        // set recipe image as unclickable until it's bitmap is loaded
        iHolder.setRecipeClickable(false)

        val title = recipe.title
        val likes = recipe.likes.toString()
        iHolder.bindText(title, likes)

        // if recipe is returning, then the id's match
        if (selectedRecipe?.id == recipe.id) {
            iHolder.setBitmapImageSource(
                selectedBitmap, // this way we use the saved bitmap
                onBitmapReady(iHolder)
            )
        } else {
            loadFromStorage(
                recipe.id,
                iHolder
            ) // this way we download the bitmap
        }
    }

    override fun onSingleRecipeClick(recipe: SingleRecipe, drawable: Drawable, view: View) {
        this.selectedRecipe = recipe
        this.selectedBitmap = drawable.toBitmap()
        iRecipesView.openRecipeDetails(recipe, this.selectedBitmap, view)
    }

    override fun onLikesButtonClick(recipe: SingleRecipe, iHolder: SingleRecipeContract.View) {
        iHolder.isLikeButtonClickable(false) // set like button as unclickable
        val shouldAdd = !likedRecipes.contains(recipe.id)
        // if it's already liked, then it should NOT push a like
        iHolder.addOrSubtractLikeFromButton(shouldAdd)
        if (!shouldAdd) likedRecipes.remove(recipe.id) else likedRecipes.add(recipe.id)
        iHolder.setLikesButtonColors(shouldAdd)
        interactor.addOrSubstractLikeFromFirestore(shouldAdd, recipe)?.addOnSuccessListener {
            iHolder.isLikeButtonClickable(true)
        }
    }

    private fun onBitmapReady(iHolder: SingleRecipeContract.View): RequestListener<Drawable?> {
        return object : RequestListener<Drawable?> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable?>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                // start transition (if it's returning) and allow clicks
                iRecipesView.startPostponedEnterTransition()
                iHolder.setRecipeClickable(true)
                return false
            }
        }
    }

    private fun loadFromStorage(
        recipeId: String,
        iHolder: SingleRecipeContract.View
    ) {
        firebaseStorage
            .getReference("recipes/recipes/")
            .child(recipeId)
            .downloadUrl
            .addOnSuccessListener { uri ->
                iHolder.setUriImageSource(uri, onBitmapReady(iHolder))
            }.addOnFailureListener {
            }
    }
}