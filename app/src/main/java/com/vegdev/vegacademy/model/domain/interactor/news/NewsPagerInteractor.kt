package com.vegdev.vegacademy.model.domain.interactor.news

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.vegdev.vegacademy.model.data.models.LearningElement
import com.vegdev.vegacademy.model.data.repositories.news.NewsRepositoryImpl

class NewsPagerInteractor {

    val repository = NewsRepositoryImpl()

    suspend fun getNewVideos(): MutableList<LearningElement> {
        return repository.getNewVideos() // TODO IF STATEMENT
    }

    suspend fun getNewArticles(): MutableList<LearningElement> {
        return repository.getNewArticles() // TODO IF STATEMENT
    }

    fun getElementCategory(path: String): Task<DocumentSnapshot> {
        return repository.getCategory(path)  // TODO IF STATEMENT
    }

}