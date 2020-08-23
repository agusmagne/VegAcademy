package com.vegdev.vegacademy.model.data.repositories

import com.vegdev.vegacademy.model.data.models.Category

interface BaseRepository {

    suspend fun getData(path: String): MutableList<Category>

}