package com.vegdev.vegacademy.model.data.repositories.recipes

import com.google.firebase.firestore.CollectionReference
import com.vegdev.vegacademy.model.data.models.Recipe

interface IRecipesRepository {

    suspend fun fetchRecipes(): MutableList<Recipe>
    fun getRecipesQuery(): CollectionReference
    fun addLike(recipeId: String)
    fun substractLike(recipeId: String)
    fun likedRecipesIdPush(userId: String, recipeId: String)
    fun likedRecipesIdRemove(userId: String, recipeId: String)

}