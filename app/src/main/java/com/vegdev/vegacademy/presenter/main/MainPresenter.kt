package com.vegdev.vegacademy.presenter.main

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.google.firebase.auth.FirebaseUser
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.User
import com.vegdev.vegacademy.model.domain.interactor.main.main.MainInteractor
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.main.MainView
import com.vegdev.vegacademy.view.news.news.NewsFragment

class MainPresenter(
    val context: Context,
    val supportFragmentManager: FragmentManager,
    val mainView: MainView,
    val interactor: MainInteractor
) : YouTubePlayer.OnInitializedListener {

    private var youtubeInterface: YouTubePlayer? = null
    private var currentYoutubeUrl: String = ""
    private val RECIPE_SENT_TEXT = "¡Receta enviada exitosamente!"
    private var YOUTUBE_BACKGROUND_HEIGHT = 0

    private var firebaseUser: FirebaseUser? = null

    fun init() {
        mainView.showProgress()
        mainView.hideNavView()

        YOUTUBE_BACKGROUND_HEIGHT = context.resources.displayMetrics.widthPixels * 9 / 16

        firebaseUser = interactor.getFirebaseUser()
        firebaseUser?.let {
            interactor.getUserInfo(it.uid).addOnSuccessListener { userSnapshot ->
                mainView.setUserInfo(userSnapshot.toObject(User::class.java))

                val newsView = mainView.getCurrentFragment() as NewsFragment
                newsView.showLayout()
                mainView.showNavView()
                mainView.hideProgress()
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
        mainView.transitionBackgroundToHeight(YOUTUBE_BACKGROUND_HEIGHT)
        mainView.showFAB()
        mainView.showPlayer()
    }

    private fun closeYoutube() {
        mainView.transitionBackgroundToHeight(0)
        mainView.hideFAB()
        mainView.hidePlayer()
    }

    private fun initializeYoutube() {
        mainView.showProgress()
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
        mainView.hideProgress()
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