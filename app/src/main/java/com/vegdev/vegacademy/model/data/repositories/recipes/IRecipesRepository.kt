package com.vegdev.vegacademy.model.data.repositories.recipes

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.vegdev.vegacademy.model.data.models.recipes.TypesRecipe

interface IRecipesRepository {

    fun getRecipesQuery(type: Any?): Query
    fun addLike(recipeId: String, type: String)
    fun substractLike(recipeId: String, type: String)
    fun likedRecipesIdPush(userId: String, recipeId: String): Task<Void>
    fun likedRecipesIdRemove(userId: String, recipeId: String): Task<Void>
    fun getSuggestionQuery(type: String): CollectionReference
    fun getSaltyRecipes(): Query
    suspend fun getAllRecipeTypes(): TypesRecipe?
    fun getFilteredRecipesQueryFromType(keywords: List<String>, type: String): Query
}