package com.vegdev.vegacademy.model.data.repositories.news

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.vegdev.vegacademy.model.data.models.learning.LearningElement

interface NewsRepository {

    suspend fun getNewVideos(): MutableList<LearningElement>
    suspend fun getNewArticles(): MutableList<LearningElement>
    fun getCategory(path: String): Task<DocumentSnapshot>
}