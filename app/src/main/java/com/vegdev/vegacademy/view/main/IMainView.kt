package com.vegdev.vegacademy.view.main

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
}