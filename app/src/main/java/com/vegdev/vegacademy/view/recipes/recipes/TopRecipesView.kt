package com.vegdev.vegacademy.view.recipes.recipes

import com.vegdev.vegacademy.presenter.recipes.toprecipes.adapter.ParentRecipesAdapter

interface TopRecipesView {

    fun buildRecipesParentRV(adapter: ParentRecipesAdapter)

}