package com.vegdev.vegacademy.model.data.repositories.recipes

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vegdev.vegacademy.model.data.models.TypesRecipe
import kotlinx.coroutines.tasks.await

class RecipesRepository : IRecipesRepository {

    private val RECIPES_DOCREF = "recipes/recipes/"
    private val USERS_COLLECTION = "users"
    private val LIKED_RECIPES_ID_FIELD = "likedRecipesId"
    private val LIKES_FIELD = "likes"
    private val PATH_SUGGESTION = "recipes/recipes/" //should be recipes/suggestions

    val firestore = Firebase.firestore

    override fun getRecipesQuery(type: Any?): CollectionReference =
        firestore.document(RECIPES_DOCREF).collection(type.toString())

    override fun addLike(recipeId: String, type: String) {
        firestore.document(RECIPES_DOCREF).collection(type).document(recipeId)
            .update(LIKES_FIELD, FieldValue.increment(1))
    }

    override fun substractLike(recipeId: String, type: String) {
        firestore.collection(RECIPES_DOCREF).document(recipeId)
            .update(LIKES_FIELD, FieldValue.increment(1), FieldValue.increment(-1))
    }

    override fun likedRecipesIdPush(userId: String, recipeId: String) {
        firestore.collection(USERS_COLLECTION).document(userId)
            .update(LIKED_RECIPES_ID_FIELD, FieldValue.arrayUnion(recipeId))
    }

    override fun likedRecipesIdRemove(userId: String, recipeId: String) {
        firestore.collection(USERS_COLLECTION).document(userId)
            .update(LIKED_RECIPES_ID_FIELD, FieldValue.arrayRemove(recipeId))
    }

    override fun getSuggestionQuery(type: String): CollectionReference {
        return firestore.document(PATH_SUGGESTION).collection(type)
    }

    override fun getSaltyRecipes(): Query {
        return firestore.collection(RECIPES_DOCREF)
    }

    override suspend fun getAllRecipeTypes(): TypesRecipe? {
        return firestore.document(RECIPES_DOCREF).get().await().toObject(TypesRecipe::class.java)
    }
}