package com.vegdev.vegacademy.contract.recipes

import com.vegdev.vegacademy.presenter.recipes.recipes.parent.adapter.ParentRecipesViewHolder
import com.vegdev.vegacademy.presenter.recipes.recipes.single.adapter.SingleRecipeAdapter

interface ParentRecipeContract {

    interface View {
        fun bindViewAndBindAdapter(type: String, adapter: SingleRecipeAdapter)
        fun showSearchBar()
        fun hideSearchBar()
    }

    interface Actions {
        fun bindViewHolder(type: String, position: Int, iHolder: View)
        fun handleSearchBarAction(
            text: String,
            type: String,
            position: Int,
            holder: ParentRecipesViewHolder
        ): Boolean
        fun onTouchSearchBarIcon(
            type: String,
            position: Int,
            holder: ParentRecipesViewHolder): android.view.View.OnTouchListener?

        fun showSearchBar(holder: ParentRecipesViewHolder)
        fun hideSearchBar(position: Int, holder: ParentRecipesViewHolder)
    }

}