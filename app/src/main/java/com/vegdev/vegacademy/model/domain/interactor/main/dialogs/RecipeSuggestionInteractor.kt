package com.vegdev.vegacademy.model.domain.interactor.main.dialogs

import com.google.android.gms.tasks.Task
import com.vegdev.vegacademy.model.data.models.Recipe
import com.vegdev.vegacademy.model.data.repositories.recipes.RecipesRepository

class RecipeSuggestionInteractor {

    private val repository = RecipesRepository()

    fun suggestRecipe(recipe: Recipe): Task<Void> {
        val docRef = repository.getSuggestionQuery().document()
        recipe.id = docRef.id
        return docRef.set(recipe)
    }
    // TODO
}