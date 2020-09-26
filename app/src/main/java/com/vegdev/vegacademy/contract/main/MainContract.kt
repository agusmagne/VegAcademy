package com.vegdev.vegacademy.contract.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator

interface MainContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onVideoClicked(url: String)
        fun openYoutubePlayer(minHeight: Int)
        fun closeYoutubePlayer()
        fun showNavView()
        fun hideNavView()
        fun getCurrentFragment(): Fragment?
        fun navigateToDirection(direction: NavDirections)
        fun makeToast(message: String)
        fun onBackPressed()
        fun showWebProgressbar()
        fun hideWebProgressbar()
        fun navigateWithOptions(
            navigationId: Int,
            bundle: Bundle,
            options: NavOptions?,
            extras: FragmentNavigator.Extras
        )
    }
    interface Actions {
        suspend fun init()
        fun closeYouTubePlayer()
        fun logOut()
        fun playVideo(url: String, isYoutubePlayerOpenOrOpening: Boolean)
    }
}