package com.vegdev.vegacademy.presenter.main

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.main.IMainView

class MainPresenter(
    val context: Context,
    val supportFragmentManager: FragmentManager,
    val iMainView: IMainView
) : YouTubePlayer.OnInitializedListener {

    private var youtubeInterface: YouTubePlayer? = null
    private var isYoutubeInitializing: Boolean = false
    private var currentYoutubeUrl: String = ""
    private var isYoutubePlayerOpen: Boolean = false
    private var youtubePlayerMinHeight = 0

    fun init() {
        youtubePlayerMinHeight = context.resources.displayMetrics.widthPixels * 9 / 16
    }

    fun closeYouTubePlayer() {
        isYoutubePlayerOpen = false
        youtubeInterface?.pause()
        currentYoutubeUrl = ""
        iMainView.closeYouTubePlayer()
    }

    fun playVideo(url: String) {

        if (youtubeInterface == null) {
            this.initializeYoutube()
        } else {
            if (isYoutubePlayerOpen) {
                if (currentYoutubeUrl == url) {
                    LayoutUtils().createToast(context, "Ya estás reproduciendo este video")
                    return //exits method immediately and wont reproduce again the same video
                }
            } else {
                iMainView.hideToolbar()
                iMainView.changePlayerBackgroundMinHeight(youtubePlayerMinHeight)
            }
            youtubeInterface?.loadVideo(url)

        }
        isYoutubePlayerOpen = true
        currentYoutubeUrl = url
    }

    private fun initializeYoutube() {
        if (!isYoutubeInitializing) {
            isYoutubeInitializing = true

            iMainView.changePlayerBackgroundMinHeight(youtubePlayerMinHeight)

            val youtubePlayerSupportFragment = YouTubePlayerSupportFragmentX.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.player, youtubePlayerSupportFragment).commit()
            youtubePlayerSupportFragment.initialize(
                context.resources.getString(R.string.API_KEY), this
            )
        }
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
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