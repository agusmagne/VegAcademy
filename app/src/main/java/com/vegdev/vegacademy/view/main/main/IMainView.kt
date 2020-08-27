package com.vegdev.vegacademy.view.main.main

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.vegdev.vegacademy.model.data.models.Filter
import com.vegdev.vegacademy.model.data.models.Recipe
import com.vegdev.vegacademy.model.data.models.User
import com.vegdev.vegacademy.view.IBaseView

interface IMainView : IBaseView {
    fun showProgress()
    fun hideProgress()
    fun showToolbar()
    fun hideToolbar()
    fun onVideoClicked(url: String)
    fun showFAB()
    fun hideFAB()
    fun transitionBackgroundToHeight(minHeight: Int)
    fun showPlayer()
    fun hidePlayer()
    fun showNavView()
    fun hideNavView()
    fun getCurrentFragment(): Fragment?
    fun navigateToDirection(direction: NavDirections)
    fun getUserInfo(): User?
    fun setUserInfo(userInfo: User?)
    fun onFilterByTitleUpdate()
    fun makeToast(message: String)
    fun updateFilters(newTitle: String, actionId: Int)
    fun updateFilters(taste: String, meal: String)
    fun getRecipeFilters(): MutableList<Filter>
    fun showRecipesSearchBar()
    fun hideRecipesSearchBar()
    fun suggestRecipe(recipe: Recipe)
}