package com.vegdev.vegacademy.model.data.repositories.recipes

import com.vegdev.vegacademy.model.data.models.Recipe

interface RecipesRepository {

    suspend fun fetchRecipes(): MutableList<Recipe>

}