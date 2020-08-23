package com.vegdev.vegacademy.model.data.repositories.learningelements

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vegdev.vegacademy.model.data.models.Category
import com.vegdev.vegacademy.model.data.models.LearningElement
import kotlinx.coroutines.tasks.await

class LearningRepositoryImpl : LearningRepository {

    private val VIDEO_CATEGORIES_PATH = "learning/videos/cat"
    private val ARTICLES_CATEGORIES_PATH = "learning/art/cat"

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
        return Firebase.firestore.collection(VIDEO_CATEGORIES_PATH)
            .get().await().toObjects(Category::class.java)
    }

    override suspend fun getArticlesCategories(): MutableList<Category> {
        return Firebase.firestore.collection(ARTICLES_CATEGORIES_PATH)
            .get().await().toObjects(Category::class.java)
    }
}