package com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes

import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.vegdev.vegacademy.model.data.models.SingleRecipe
import com.vegdev.vegacademy.model.data.models.TypesRecipe
import com.vegdev.vegacademy.model.data.repositories.recipes.RecipesRepository

class TopRecipesInteractor {

    private val repository = RecipesRepository()

    suspend fun getAllRecipeTypes(): TypesRecipe? {
        return repository.getAllRecipeTypes()
    }

    fun getPaginatedRecipesFromType(type: Any?): FirestorePagingOptions<SingleRecipe> {
        val query = repository.getRecipesQuery(type)
        val config = PagedList.Config.Builder().setInitialLoadSizeHint(3).setPageSize(3).build()
        return FirestorePagingOptions.Builder<SingleRecipe>()
            .setQuery(query, config, SingleRecipe::class.java).build()
    }
}