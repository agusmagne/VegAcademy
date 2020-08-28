package com.vegdev.vegacademy.model.domain.interactor.recipes

import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.vegdev.vegacademy.model.data.models.Filter
import com.vegdev.vegacademy.model.data.models.Recipe
import com.vegdev.vegacademy.model.data.repositories.recipes.RecipesRepository
import java.util.*

class RecipesInteractor {

    private val FILTER_TITLE = 0
    private val FILTER_TASTE = 1
    private val FILTER_MEAL = 2
    private val FILTER_BOTH = "Ambos"
    private val repository = RecipesRepository()
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    val filters: MutableList<Filter> = mutableListOf()

    fun getFilteredPaginatedRecipes(): FirestorePagingOptions<Recipe> {
        val query = this.buildRecipesQuery()
        val config = PagedList.Config.Builder().setInitialLoadSizeHint(5).setPageSize(3).build()
        return FirestorePagingOptions.Builder<Recipe>()
            .setQuery(query, config, Recipe::class.java)
            .build()
    }

    fun addOrSubstractLikeFromFirestore(shouldAdd: Boolean, recipeId: String) {
        firebaseUser?.let { user ->
            // adds/remove like to recipe document & adds/remove recipe from user favorites recipes
            if (shouldAdd) {
                repository.addLike(recipeId)
                repository.likedRecipesIdPush(user.uid, recipeId)
            }

            if (!shouldAdd) {
                repository.substractLike(recipeId)
                repository.likedRecipesIdRemove(user.uid, recipeId)
            }
        }
    }

    private fun buildRecipesQuery(): Query {
        var query: Query = repository.getRecipesQuery()
        val titleFilter = filters.filter { it.type == FILTER_TITLE }
        val tasteFilter = filters.filter { it.type == FILTER_TASTE }
        val mealFilter = filters.filter { it.type == FILTER_MEAL }

        if (titleFilter.isNotEmpty()) {
            val filterString = titleFilter[0].title
            query = query.whereGreaterThanOrEqualTo("title", filterString)
                .whereLessThan("title", getFilterTo(filterString))
        }

        if (tasteFilter.isNotEmpty()) {
            val filterString = tasteFilter[0].title.toLowerCase(Locale.ROOT)
            query = query.whereEqualTo("taste", filterString)
        }

        if (mealFilter.isNotEmpty()) {
            val filterString = mealFilter[0].title.toLowerCase(Locale.ROOT)
            query = query.whereEqualTo("meal", filterString)
        }
        return query
    }

    private fun getFilterTo(string: String): String {
        // when filtering from a string to a string, this method builds filterTo string
        val length = string.length
        val lastLetter = string.substring(length - 1, length)
        val stringWithoutLastLetter = string.toLowerCase(Locale.ROOT).substring(0, length - 1)
        return stringWithoutLastLetter + (lastLetter.toCharArray()[0] + 1).toString()
    }

    fun updateFilters(byTitle: String): Boolean {
        // remove old filter by title
        // returns true if new title is the same as old title filter
        val filterByTitle = filters.filter { it.type == FILTER_TITLE }
        return if (filterByTitle.isNotEmpty()) {
            if (filterByTitle[0].title == byTitle) {
                true
            } else {
                filters.remove(filterByTitle[0])
                filters.add(Filter(byTitle, FILTER_TITLE))
                false
            }
        } else {
            filters.add(Filter(byTitle, FILTER_TITLE))
            false
        }
    }

    fun updateFilters(byTaste: String, byMeal: String) {

        // erase old filters and update them with new ones
        val filterByTaste = filters.filter { it.type == FILTER_TASTE }
        if (filterByTaste.isNotEmpty()) {
            if (filterByTaste[0].title != byTaste) {
                filters.remove(filterByTaste[0])
                if (byTaste != FILTER_BOTH) {
                    filters.add(Filter(byTaste, FILTER_TASTE))
                }
            }
        } else {
            if (byTaste != FILTER_BOTH) {
                filters.add(Filter(byTaste, FILTER_TASTE))
            }
        }

        val filterByMeal = filters.filter { it.type == FILTER_MEAL }
        if (filterByMeal.isNotEmpty()) {
            if (filterByMeal[0].title != byMeal) {
                filters.remove(filterByMeal[0])
                if (byMeal != FILTER_BOTH) {
                    filters.add(Filter(byMeal, FILTER_MEAL))
                }
            }
        } else {
            if (byMeal != FILTER_BOTH) {
                filters.add(Filter(byMeal, FILTER_MEAL))
            }
        }
    }

    fun removeFilter(filter: Filter) {
        filters.remove(filter)
    }
}