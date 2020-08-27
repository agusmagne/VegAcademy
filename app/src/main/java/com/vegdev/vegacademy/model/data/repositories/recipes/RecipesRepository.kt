package com.vegdev.vegacademy.model.data.repositories.recipes

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vegdev.vegacademy.model.data.models.Recipe
import kotlinx.coroutines.tasks.await

class RecipesRepository : IRecipesRepository {

    private val RECIPE_COLLECTION = "rec"
    private val USERS_COLLECTION = "users"
    private val LIKED_RECIPES_ID_FIELD = "likedRecipesId"

    val firestore = Firebase.firestore

    override suspend fun fetchRecipes(): MutableList<Recipe> =
        firestore.collection(RECIPE_COLLECTION).get().await().toObjects(Recipe::class.java)

    override fun getRecipesQuery(): CollectionReference =
        firestore.collection(RECIPE_COLLECTION)

    override fun addLike(recipeId: String) {
        firestore.collection(RECIPE_COLLECTION).document(recipeId).update(
            "likes", FieldValue.increment(1)
        )
    }

    override fun substractLike(recipeId: String) {
        firestore.collection(RECIPE_COLLECTION).document(recipeId).update(
            "likes", FieldValue.increment(-1)
        )
    }

    override fun likedRecipesIdPush(userId: String, recipeId: String) {
        firestore.collection(USERS_COLLECTION).document(userId)
            .update(LIKED_RECIPES_ID_FIELD, FieldValue.arrayUnion(recipeId))
    }

    override fun likedRecipesIdRemove(userId: String, recipeId: String) {
        firestore.collection(USERS_COLLECTION).document(userId)
            .update(LIKED_RECIPES_ID_FIELD, FieldValue.arrayRemove(recipeId))
    }


}