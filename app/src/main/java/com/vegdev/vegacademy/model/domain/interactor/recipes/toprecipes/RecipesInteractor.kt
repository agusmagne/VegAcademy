package com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes

import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.Query
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.data.models.TypesRecipe
import com.vegdev.vegacademy.model.data.repositories.recipes.RecipesRepository

class RecipesInteractor {

    private val repository = RecipesRepository()

    suspend fun getAllRecipeTypes(): TypesRecipe? {
        return repository.getAllRecipeTypes()
    }

    fun getPaginatedRecipesFromType(type: Any?): FirestorePagingOptions<SingleRecipe> {
        val query = repository.getRecipesQuery(type)
        return buildFirestorePagingOptions(query)
    }

    fun getPaginatedFilteredRecipesFromType(
        string: String,
        type: String
    ): FirestorePagingOptions<SingleRecipe> {
        val query = repository.getFilteredRecipesQueryFromType(string.split(" "), type)
        return buildFirestorePagingOptions(query)
    }

    private fun buildFirestorePagingOptions(query: Query): FirestorePagingOptions<SingleRecipe> {
        val config = PagedList.Config.Builder().setInitialLoadSizeHint(3).setPageSize(3).build()
        return FirestorePagingOptions.Builder<SingleRecipe>()
            .setQuery(query, config, SingleRecipe::class.java).build()
    }
}