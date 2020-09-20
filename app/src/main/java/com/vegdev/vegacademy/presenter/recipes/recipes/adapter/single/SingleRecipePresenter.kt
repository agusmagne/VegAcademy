package com.vegdev.vegacademy.presenter.recipes.recipes.adapter.single

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.storage.FirebaseStorage
import com.vegdev.vegacademy.contract.RecipesContract
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.view.main.main.MainView

class SingleRecipePresenter(
    private val iRecipesView: RecipesContract.View,
    private val iMainView: MainView
) {

    private val firebaseStorage =
        FirebaseStorage.getInstance()

    private var selectedBitmap: Bitmap? = null
    private var selectedRecipe: SingleRecipe? = null

    fun bindRecipe(recipe: SingleRecipe, iHolder: RecipesContract.View.SingleRecipeView) {
        val title = recipe.title
        val likes = recipe.likes.toString()

        if (selectedRecipe?.id == recipe.id) {
            iHolder.bindReturningRecipe(
                title,
                likes,
                selectedBitmap,
                iRecipesView
            )
        } else {
            this.loadFromStorage(title, likes, recipe.id, iHolder)
        }
    }

    private fun loadFromStorage(
        title: String,
        likes: String,
        recipeId: String,
        iHolder: RecipesContract.View.SingleRecipeView
    ) {
        firebaseStorage
            .getReference("recipes/recipes/")
            .child(recipeId)
            .downloadUrl
            .addOnSuccessListener {
                iHolder.bindRecipe(title, likes, it)
            }.addOnFailureListener {
                iMainView.makeToast("Error al cargar imagen de la receta $title")
            }
    }

    fun onSingleRecipeClick(recipe: SingleRecipe, drawable: Drawable, view: View) {
        this.selectedRecipe = recipe
        this.selectedBitmap = drawable.toBitmap()
        iRecipesView.openRecipeDetails(recipe, this.selectedBitmap, view)
    }
}