package com.vegdev.vegacademy.data.repositories.learningelements

import com.vegdev.vegacademy.data.models.Category
import com.vegdev.vegacademy.data.models.LearningElement

interface LearningRepository {

    suspend fun getVideosFromCategory(category: String): MutableList<LearningElement>
    suspend fun getArticlesFromCategory(category: String): MutableList<LearningElement>
    suspend fun getVideoCategories(): MutableList<Category>
    suspend fun getArticlesCategories(): MutableList<Category>

}