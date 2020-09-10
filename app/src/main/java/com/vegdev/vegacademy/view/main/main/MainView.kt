package com.vegdev.vegacademy.view.main.main

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.vegdev.vegacademy.model.data.models.User
import com.vegdev.vegacademy.view.BaseView

interface MainView : BaseView {
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
    fun showRecipesSearchBar()
    fun hideRecipesSearchBar()
    fun onBackPressed()
}