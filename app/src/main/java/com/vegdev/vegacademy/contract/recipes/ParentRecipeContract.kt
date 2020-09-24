package com.vegdev.vegacademy.contract.recipes

import android.view.View
import com.vegdev.vegacademy.presenter.recipes.recipes.single.adapter.SingleRecipeAdapter

interface ParentRecipeContract {

    interface View {
        fun bindViewAndBindAdapter(type: String, adapter: SingleRecipeAdapter)
        fun showSearchBar()
        fun hideSearchBar()
        fun onTouchSearchBarIcon(): android.view.View.OnTouchListener?
    }

    interface Actions {

    }

}