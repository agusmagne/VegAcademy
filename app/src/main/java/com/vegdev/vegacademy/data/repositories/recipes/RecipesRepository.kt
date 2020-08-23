package com.vegdev.vegacademy.data.repositories.recipes

import com.vegdev.vegacademy.data.models.Recipe

interface RecipesRepository {

    suspend fun fetchRecipes(): MutableList<Recipe>

}