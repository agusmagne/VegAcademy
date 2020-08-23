package com.vegdev.vegacademy.domain

import com.vegdev.vegacademy.data.models.Recipe
import com.vegdev.vegacademy.data.repositories.recipes.RecipesRepositoryImpl
import com.vegdev.vegacademy.utils.schedulers.BaseSchedulerProvider

class RecipesUseCase(private val scheduler: BaseSchedulerProvider) :
    BaseUseCase<MutableList<Recipe>> {

    private val recipesRepository = RecipesRepositoryImpl()


}