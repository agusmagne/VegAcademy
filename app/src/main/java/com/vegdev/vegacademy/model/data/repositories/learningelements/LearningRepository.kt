package com.vegdev.vegacademy.model.data.repositories.learningelements

import com.vegdev.vegacademy.model.data.models.Category
import com.vegdev.vegacademy.model.data.models.LearningElement

interface LearningRepository {

    suspend fun getVideosFromCategory(category: String): MutableList<LearningElement>
    suspend fun getArticlesFromCategory(category: String): MutableList<LearningElement>
    suspend fun getVideoCategories(): MutableList<Category>
    suspend fun getArticlesCategories(): MutableList<Category>

}