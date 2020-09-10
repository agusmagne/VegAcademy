package com.vegdev.vegacademy.model.data.repositories.news

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vegdev.vegacademy.model.data.models.LearningElement
import kotlinx.coroutines.tasks.await

class NewsRepositoryImpl : NewsRepository {

    private val VIDEO_NEWS_PATH = "videoNews"
    private val ARTICLES_NEWS_PATH = "articleNews"

    override suspend fun getNewVideos(): MutableList<LearningElement> =
        Firebase.firestore.collection(VIDEO_NEWS_PATH).get().await()
            .toObjects(LearningElement::class.java)


    override suspend fun getNewArticles(): MutableList<LearningElement> =
        Firebase.firestore.collection(ARTICLES_NEWS_PATH).get().await()
            .toObjects(LearningElement::class.java)


    override fun getCategory(path: String): Task<DocumentSnapshot> = Firebase.firestore
        .document(path).get()

}