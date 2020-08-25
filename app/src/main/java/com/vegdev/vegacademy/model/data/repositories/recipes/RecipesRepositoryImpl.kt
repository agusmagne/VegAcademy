package com.vegdev.vegacademy.model.data.repositories.recipes

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vegdev.vegacademy.model.data.models.Recipe
import kotlinx.coroutines.tasks.await

class RecipesRepositoryImpl : RecipesRepository {


    override suspend fun fetchRecipes(): MutableList<Recipe> {
        return Firebase.firestore.collection("rec").get().await().toObjects(Recipe::class.java)
    }


}