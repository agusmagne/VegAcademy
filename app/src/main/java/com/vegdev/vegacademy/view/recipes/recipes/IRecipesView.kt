package com.vegdev.vegacademy.view.recipes.recipes

import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.vegdev.vegacademy.model.data.models.Filter
import com.vegdev.vegacademy.model.data.models.Recipe
import com.vegdev.vegacademy.view.IBaseView
import com.vegdev.vegacademy.view.recipes.filters.FiltersAdapter
import com.vegdev.vegacademy.view.recipes.recipes.adapter.RecipesViewHolder

interface IRecipesView : IBaseView {

    fun buildFiltersRecyclerView(adapter: FirestorePagingAdapter<Recipe, RecipesViewHolder>)
    fun buildFiltersRecyclerView(adapter: FiltersAdapter)
    fun updateFilters(newTitle: String, actionId: Int)
    fun updateFilters(taste: String, meal: String)
    fun getFilters(): MutableList<Filter>

}