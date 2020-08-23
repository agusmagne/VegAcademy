package com.vegdev.vegacademy.domain.interactor.learning

import android.util.Log
import com.vegdev.vegacademy.data.models.Category
import com.vegdev.vegacademy.data.repositories.learningelements.LearningRepositoryImpl

class LearningInteractor {

    private val repository = LearningRepositoryImpl()
    private val TAG_ERROR = "ERROR"
    private val MSG_ERROR = "Hubo un error al descargar las categorías de los videos"

    suspend fun getVideoCategories(): MutableList<Category> {
        try {
            val list = repository.getVideoCategories()
            if (list.isNotEmpty()) {
                return list
            }
        } catch (e: Exception) {
            Log.d(TAG_ERROR, MSG_ERROR)
        }
        return mutableListOf()
    }

}