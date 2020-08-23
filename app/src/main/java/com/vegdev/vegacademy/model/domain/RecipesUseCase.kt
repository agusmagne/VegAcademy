package com.vegdev.vegacademy.model.domain

import com.vegdev.vegacademy.model.data.models.Recipe
import com.vegdev.vegacademy.model.data.repositories.recipes.RecipesRepositoryImpl
import com.vegdev.vegacademy.utils.schedulers.BaseSchedulerProvider

class RecipesUseCase(private val scheduler: BaseSchedulerProvider) :
    BaseUseCase<MutableList<Recipe>> {

    private val recipesRepository = RecipesRepositoryImpl()


}