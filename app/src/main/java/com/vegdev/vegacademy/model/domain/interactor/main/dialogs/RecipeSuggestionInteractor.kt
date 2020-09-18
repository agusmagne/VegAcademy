package com.vegdev.vegacademy.model.domain.interactor.main.dialogs

import com.google.android.gms.tasks.Task
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.data.repositories.recipes.RecipesRepository

class RecipeSuggestionInteractor {

    private val repository = RecipesRepository()

    fun suggestRecipe(recipe: SingleRecipe): Task<Void> {
        val docRef = repository.getSuggestionQuery(recipe.type).document()
        recipe.id = docRef.id
        return docRef.set(recipe)
    }
}