package com.vegdev.vegacademy.data.repositories.learningelements

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vegdev.vegacademy.data.models.Category
import com.vegdev.vegacademy.data.models.LearningElement
import kotlinx.coroutines.tasks.await

class LearningRepositoryImpl : LearningRepository {

    override suspend fun getVideosFromCategory(category: String): MutableList<LearningElement> {
        return Firebase.firestore.collection("learning").document("videos")
            .collection(category).get().await().toObjects(LearningElement::class.java)
    }

    override suspend fun getArticlesFromCategory(category: String): MutableList<LearningElement> {
        return Firebase.firestore.collection("learning")
            .document("art")
            .collection(category)
            .get().await().toObjects(LearningElement::class.java)
    }

    override suspend fun getVideoCategories(): MutableList<Category> {
        return Firebase.firestore.collection("learning")
            .document("videos")
            .collection("cat")
            .get().await().toObjects(Category::class.java)
    }

    override suspend fun getArticlesCategories(): MutableList<Category> {
        return Firebase.firestore.collection("learning")
            .document("art")
            .collection("cat")
            .get().await().toObjects(Category::class.java)
    }
}