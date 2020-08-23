package com.vegdev.vegacademy.model.domain.interactor.learning

import android.util.Log
import com.vegdev.vegacademy.model.data.models.Category
import com.vegdev.vegacademy.model.data.repositories.learning.categories.LearningRepositoryImpl

class CategoriesInteractor {

    private val TAG_ERROR = "ERROR"
    private val MSG_ERROR = "Hubo un error al descargar las categorías"
    private val PATH_VIDEO_CATEGORIES = "learning/videos/cat"
    private val PATH_ARTICLES_CATEGORIES = "learning/videos/"

    private val repository =
        LearningRepositoryImpl()

    suspend fun getVideoCategories(): MutableList<Category> {
        try {
            val list = repository.getData(PATH_VIDEO_CATEGORIES)
            if (list.isNotEmpty()) return list
        } catch (e: Exception) {
            Log.d(TAG_ERROR, MSG_ERROR)
        }
        return mutableListOf()
    }

    suspend fun getArticlesCategories(): MutableList<Category> {
        try {
            val list = repository.getData(PATH_ARTICLES_CATEGORIES)
            if (list.isNotEmpty()) return list
        } catch (e: Exception) {
            Log.d(TAG_ERROR, MSG_ERROR)
        }
        return mutableListOf()
    }
}