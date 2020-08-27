package com.vegdev.vegacademy.presenter.recipes

import android.content.Context
import android.view.inputmethod.EditorInfo
import com.vegdev.vegacademy.model.domain.interactor.recipes.RecipesInteractor
import com.vegdev.vegacademy.view.main.main.IMainView
import com.vegdev.vegacademy.view.recipes.filters.FiltersAdapter
import com.vegdev.vegacademy.view.recipes.recipes.IRecipesView
import com.vegdev.vegacademy.view.recipes.recipes.adapter.RecipesAdapter

class RecipesPresenter(
    val context: Context,
    val iMainView: IMainView,
    val iRecipesView: IRecipesView,
    val interactor: RecipesInteractor
) {

    private val ERROR_SAME_TITLE = "Ya estás buscando: "
    private val ERROR_NEWTITLE_LENGHT = "Debes ingresar por lo menos 3 caracteres"
    private val ERROR_SAME_FILTERS = "No haz cambiado ningún filtro"
    private lateinit var filtersAdapter: FiltersAdapter
    private lateinit var recipesAdapter: RecipesAdapter

    fun buildRecipesRecyclerView() {
        iMainView.showProgress()
        iRecipesView.hideLayout()

        val options = interactor.getFilteredPaginatedRecipes()
        val likedRecipesId = iMainView.getUserInfo()?.likedRecipesId

        recipesAdapter = RecipesAdapter(options, likedRecipesId, { recipe ->
            // on recipe image click
        }, { shouldAdd, recipeId ->
            // on like btn click
            interactor.addOrSubstractLikeFromFirestore(shouldAdd, recipeId)
        })
        iRecipesView.buildFiltersRecyclerView(recipesAdapter)

        iMainView.hideProgress()
        iRecipesView.showLayout()
    }

    fun buildFiltersRecyclerView() {
        filtersAdapter = FiltersAdapter(interactor.filters) {
            interactor.removeFilter(it)
            filtersAdapter.notifyDataSetChanged()
            val options = interactor.getFilteredPaginatedRecipes()
            recipesAdapter.updateOptions(options)
        }
        iRecipesView.buildFiltersRecyclerView(filtersAdapter)
    }

    fun updateFilters(newTitle: String, actionId: Int) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (newTitle.length >= 3) {

                val isTitleFilterTheSame = interactor.updateFilters(newTitle)
                if (isTitleFilterTheSame) {
                    iMainView.makeToast(ERROR_SAME_TITLE + newTitle)
                } else {
                    // filter changed so update & notify adapters
                    val options = interactor.getFilteredPaginatedRecipes()
                    recipesAdapter.updateOptions(options)
                    filtersAdapter.notifyDataSetChanged()
                }
            }
        } else {
            iMainView.makeToast(ERROR_NEWTITLE_LENGHT)
        }
    }

    fun updateFilters(taste: String, meal: String) {
        val didAnyFilterChange = interactor.updateFilters(taste, meal)
        if (didAnyFilterChange) {
            val options = interactor.getFilteredPaginatedRecipes()
            recipesAdapter.updateOptions(options)
            filtersAdapter.notifyDataSetChanged()
        } else {
            iMainView.makeToast(ERROR_SAME_FILTERS)
        }
    }

    fun showRecipesSearchBar() {
        iMainView.showRecipesSearchBar()
    }

    fun hideRecipesSearchBar() {
        iMainView.hideRecipesSearchBar()
    }
}