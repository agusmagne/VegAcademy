package com.vegdev.vegacademy.presenter.main

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.google.firebase.auth.FirebaseUser
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.domain.interactor.main.MainInteractor
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.main.IMainView
import com.vegdev.vegacademy.view.news.NewsView

class MainPresenter(
    val context: Context,
    val supportFragmentManager: FragmentManager,
    val iMainView: IMainView,
    val interactor: MainInteractor
) : YouTubePlayer.OnInitializedListener {

    private var youtubeInterface: YouTubePlayer? = null
    private var currentYoutubeUrl: String = ""
    private var YOUTUBE_BACKGROUND_HEIGHT = 0

    private var firebaseUser: FirebaseUser? = null

    fun init() {
        iMainView.showprogress()
        iMainView.hideNavView()

        YOUTUBE_BACKGROUND_HEIGHT = context.resources.displayMetrics.widthPixels * 9 / 16

        firebaseUser = interactor.getFirebaseUser()
        firebaseUser?.let {
            interactor.getUserInfo(it.uid).addOnSuccessListener {
                val newsView = iMainView.getCurrentFragment() as NewsView
                newsView.showLayout()
                iMainView.showNavView()
                iMainView.hideprogress()
            }
        }
    }

    fun closeYouTubePlayer() {
        youtubeInterface?.pause()
        currentYoutubeUrl = ""
        this.closeYoutube()
    }

    fun playVideo(url: String, isYoutubePlayerOpenOrOpening: Boolean) {

        if (youtubeInterface == null) {
            // youtube interface is not initialized yet
            currentYoutubeUrl = url
            this.initializeYoutube()
        } else {
            if (!isYoutubePlayerOpenOrOpening) {
                // it is initialized now, but it's closed
                this.openYoutube()
            } else {
                // it is initialized and it's open
                if (currentYoutubeUrl == url) {
                    LayoutUtils().createToast(context, "Ya estás reproduciendo este video")
                    return //exits method immediately and wont reproduce again the same video
                }

            }
            currentYoutubeUrl = url
            youtubeInterface?.loadVideo(url) // if currentYoutubeUrl == url -> this line is unreachable

        }
    }

    private fun openYoutube() {
        iMainView.transitionBackgroundToHeight(YOUTUBE_BACKGROUND_HEIGHT)
        iMainView.showFAB()
        iMainView.showPlayer()
    }

    private fun closeYoutube() {
        iMainView.transitionBackgroundToHeight(0)
        iMainView.hideFAB()
        iMainView.hidePlayer()
    }

    private fun initializeYoutube() {
        iMainView.showprogress()
        val youtubePlayerSupportFragment = YouTubePlayerSupportFragmentX.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.player, youtubePlayerSupportFragment).commit()
        youtubePlayerSupportFragment.initialize(
            context.resources.getString(R.string.API_KEY), this
        )
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        iMainView.hideprogress()
        this.openYoutube() //---------------------------------------
        //this animation needs to be here otherwise the initialization of the youtube interface glitches the animation

        p1?.loadVideo(currentYoutubeUrl)
        youtubeInterface = p1
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        LayoutUtils().createToast(context, "Error al iniciar Youtube")
    }

}