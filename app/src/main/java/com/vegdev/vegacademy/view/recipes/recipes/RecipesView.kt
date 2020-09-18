package com.vegdev.vegacademy.view.recipes.recipes

import com.vegdev.vegacademy.presenter.recipes.recipes.adapter.parent.ParentRecipesAdapter

interface RecipesView {

    fun buildRecipesParentRV(adapter: ParentRecipesAdapter)
    fun startPostponedEnterTransition()

}