package com.vegdev.vegacademy.model.domain.interactor.recipes.recipes

import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.model.data.models.recipes.SingleRecipe
import com.vegdev.vegacademy.model.data.repositories.recipes.RecipesRepository

class DeprecatedRecipesInteractor {

    private val repository = RecipesRepository()
    private val firebaseUser = FirebaseAuth.getInstance().currentUser

    fun getFilteredPaginatedRecipes(type: String): FirestorePagingOptions<SingleRecipe> {
        val query = repository.getRecipesQuery(type)
        val config = PagedList.Config.Builder().setInitialLoadSizeHint(5).setPageSize(3).build()
        return FirestorePagingOptions.Builder<SingleRecipe>()
            .setQuery(query, config, SingleRecipe::class.java)
            .build()
    }

    fun addOrSubstractLikeFromFirestore(shouldAdd: Boolean, recipe: SingleRecipe) {
        firebaseUser?.let { user ->
            // adds/remove like to recipe document & adds/remove recipe from user favorites recipes
            if (shouldAdd) {
                repository.addLike(recipe.id, recipe.type)
                repository.likedRecipesIdPush(user.uid, recipe.id)
            }

            if (!shouldAdd) {
                repository.substractLike(recipe.id, recipe.type)
                repository.likedRecipesIdRemove(user.uid, recipe.id)
            }
        }
    }
}