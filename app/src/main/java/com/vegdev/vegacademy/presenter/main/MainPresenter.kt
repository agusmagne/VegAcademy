package com.vegdev.vegacademy.presenter.main

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.model.data.dataholders.UserDataHolder
import com.vegdev.vegacademy.utils.Utils
import com.vegdev.vegacademy.view.login.StartActivity
import com.vegdev.vegacademy.view.news.NewsFragment

class MainPresenter(
    private val context: Context,
    private val supportFragmentManager: FragmentManager,
    private val iView: MainContract.View
) : MainContract.Actions, YouTubePlayer.OnInitializedListener {

    private var youtubeInterface: YouTubePlayer? = null
    private var currentYoutubeUrl: String = ""
    private var YOUTUBE_BACKGROUND_HEIGHT = 0


    override suspend fun init() {
        iView.closeYoutubePlayer()
        iView.showProgress()
        iView.hideNavView()

        // get user information
        UserDataHolder.getUserData()

        // calculate the height that youtube player's going to have
        YOUTUBE_BACKGROUND_HEIGHT = context.resources.displayMetrics.widthPixels * 9 / 16

        val newsView = iView.getCurrentFragment() as NewsFragment
        newsView.showLayout()
        iView.showNavView()
        iView.hideProgress()
    }

    override fun closeYouTubePlayer() {
        youtubeInterface?.pause()
        currentYoutubeUrl = ""
        iView.closeYoutubePlayer()
    }

    override fun playVideo(url: String, isYoutubePlayerOpenOrOpening: Boolean) {

        if (youtubeInterface == null) {
            // youtube interface is not initialized yet
            currentYoutubeUrl = url
            this.initializeYoutube()
        } else {
            if (!isYoutubePlayerOpenOrOpening) {
                // it is initialized now, but it's closed
                iView.openYoutubePlayer(YOUTUBE_BACKGROUND_HEIGHT)
            } else {
                // it is initialized and it's open
                if (currentYoutubeUrl == url) {
                    Utils().createToast(context, "Ya estás reproduciendo este video")
                    return //exits method immediately and wont reproduce again the same video
                }
            }
            currentYoutubeUrl = url
            youtubeInterface?.loadVideo(url) // if currentYoutubeUrl == url -> this line is unreachable
        }
    }

    override fun logOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context, StartActivity::class.java)
        context.startActivity(intent)
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        iView.hideProgress()
        iView.openYoutubePlayer(YOUTUBE_BACKGROUND_HEIGHT) //---------------------------------------
        //this animation needs to be here otherwise the initialization of the youtube interface glitches the animation

        p1?.loadVideo(currentYoutubeUrl)
        youtubeInterface = p1
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Utils().createToast(context, "Error al iniciar Youtube")
    }

    private fun initializeYoutube() {
        iView.showProgress()
        val youtubePlayerSupportFragment = YouTubePlayerSupportFragmentX.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.player, youtubePlayerSupportFragment).commit()
        youtubePlayerSupportFragment.initialize(
            context.resources.getString(R.string.API_KEY), this
        )
    }
}