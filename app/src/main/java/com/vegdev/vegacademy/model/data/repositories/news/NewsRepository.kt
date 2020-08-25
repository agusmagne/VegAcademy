package com.vegdev.vegacademy.model.data.repositories.news

import com.vegdev.vegacademy.model.data.models.LearningElement

interface NewsRepository {

    suspend fun getNewVideos(): MutableList<LearningElement>
    suspend fun getNewArticles(): MutableList<LearningElement>
}