package com.vegdev.vegacademy.view.main

import androidx.fragment.app.Fragment

interface IMainView {
    fun showprogress()
    fun hideprogress()
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
}