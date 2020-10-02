package com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes

import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.vegdev.vegacademy.model.data.models.recipes.SingleRecipe
import com.vegdev.vegacademy.model.data.models.recipes.TypesRecipe
import com.vegdev.vegacademy.model.data.repositories.recipes.RecipesRepository

class RecipesInteractor {

    private val repository = RecipesRepository()
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun getAllRecipeTypes(): TypesRecipe? {
        return repository.getAllRecipeTypes()
    }

    fun getPaginatedRecipesFromType(type: Any?): FirestorePagingOptions<SingleRecipe> {
        val query = repository.getRecipesQuery(type)
        return buildFirestorePagingOptions(query)
    }

    fun getPaginatedFilteredRecipesFromType(
        string: String,
        type: String
    ): FirestorePagingOptions<SingleRecipe> {
        val query = repository.getFilteredRecipesQueryFromType(string.split(" "), type)
        return buildFirestorePagingOptions(query)
    }

    fun addOrSubstractLikeFromFirestore(shouldAdd: Boolean, recipe: SingleRecipe): Task<Void>? {
        firebaseAuth.currentUser?.let { user ->
            // adds/remove like to recipe document & adds/remove recipe from user favorites recipes
            return if (shouldAdd) {
                repository.addLike(recipe.id, recipe.type)
                repository.likedRecipesIdPush(user.uid, recipe.id)
            } else {
                repository.substractLike(recipe.id, recipe.type)
                repository.likedRecipesIdRemove(user.uid, recipe.id)
            }
        }
        return null
    }

    fun getUserLikedRecipes(): MutableList<String> {
        firebaseAuth.currentUser?.let {
            val userId = it.uid
        }
        return mutableListOf()
    }

    private fun buildFirestorePagingOptions(query: Query): FirestorePagingOptions<SingleRecipe> {
        val config = PagedList.Config.Builder().setInitialLoadSizeHint(3).setPageSize(3).build()
        return FirestorePagingOptions.Builder<SingleRecipe>()
            .setQuery(query, config, SingleRecipe::class.java).build()
    }
}