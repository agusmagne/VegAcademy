package com.vegdev.vegacademy.model.data.repositories.learning.categories

import com.vegdev.vegacademy.model.data.models.Category

interface CategoriesRepository {

    suspend fun getCategories(path: String): MutableList<Category>
}