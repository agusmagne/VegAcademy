package com.vegdev.vegacademy.presenter.recipes.recipes.parent

import com.vegdev.vegacademy.contract.recipes.ParentRecipeContract
import com.vegdev.vegacademy.contract.recipes.RecipesContract
import com.vegdev.vegacademy.model.domain.interactor.recipes.toprecipes.RecipesInteractor
import com.vegdev.vegacademy.presenter.recipes.recipes.single.adapter.SingleRecipeAdapter

class ParentRecipesPresenter(
    private val iRecipesView: RecipesContract.View
) : ParentRecipeContract.Actions {

    private val interactor = RecipesInteractor()
    private val adapters: MutableList<SingleRecipeAdapter> = mutableListOf()

    fun bindViewHolder(type: String, position: Int, iHolder: ParentRecipeContract.View) {
        if (adapters.size < position + 1) {
            adapters.add(createAdapter(type))
        }
        iHolder.bindViewAndBindAdapter(
            type,
            adapters[position]
        )
    }

    private fun createAdapter(type: String): SingleRecipeAdapter {
        val recipesOptions = interactor.getPaginatedRecipesFromType(type)
        return SingleRecipeAdapter(recipesOptions, iRecipesView)
    }
}