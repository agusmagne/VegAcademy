package com.vegdev.vegacademy.model.data.repositories.learning.categories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vegdev.vegacademy.model.data.models.Category
import kotlinx.coroutines.tasks.await

class LearningRepositoryImpl :
    LearningRepository {

    override suspend fun getData(path: String): MutableList<Category> {
        return Firebase.firestore.collection(path).get().await().toObjects(Category::class.java)
    }
}