package com.vegdev.vegacademy.model.data.repositories.recipes

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.vegdev.vegacademy.model.data.models.TypesRecipe

interface IRecipesRepository {

    fun getRecipesQuery(type: Any?): CollectionReference
    fun addLike(recipeId: String, type: String)
    fun substractLike(recipeId: String, type: String)
    fun likedRecipesIdPush(userId: String, recipeId: String)
    fun likedRecipesIdRemove(userId: String, recipeId: String)
    fun getSuggestionQuery(type: String): CollectionReference
    fun getSaltyRecipes(): Query
    suspend fun getAllRecipeTypes(): TypesRecipe?
}