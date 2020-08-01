package com.vegdev.vegacademy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionManager
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.login.StartActivity
import com.vegdev.vegacademy.login.WelcomeActivity
import com.vegdev.vegacademy.ui.news.NewsFragment
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IYoutubePlayer, IProgressBar, IToolbar {

    private val layoutUtils = LayoutUtils()
    lateinit var firebaseAuth: FirebaseAuth
    private var youtubePlayer: YouTubePlayer? = null
    private var isYoutubePlayerOpen: Boolean = false
    private var isYoutubeInitialized: Boolean = false
    private var currentLink: String = ""
    private var incomingLink: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_toolbar.visibility = View.VISIBLE

        firebaseAuth = FirebaseAuth.getInstance()

        setSupportActionBar(main_toolbar)
        supportActionBar?.title = ""

        val navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)

        // set FAB click listener allowing to close youtube player interface
        fab_closeyoutube.hide()
        fab_closeyoutube.setOnClickListener {
            isYoutubePlayerOpen = false
            TransitionManager.beginDelayedTransition(container)
            fab_closeyoutube.hide()
            player_background.minHeight = 0
            youtubePlayer?.pause()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_logout -> {
                firebaseAuth.signOut()
                val intent = Intent(this, StartActivity::class.java)
                this.startActivity(intent)
                layoutUtils.overrideEnterAndExitTransitions(this)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val currentFragment =
            fragment?.childFragmentManager?.fragments?.get(0)

        if (currentFragment is NewsFragment) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra("EXIT", true)
                startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }

    override fun openYoutubePlayer(link: String) {
        if (fab_closeyoutube.isOrWillBeHidden) fab_closeyoutube.show()
        incomingLink = link
        if (youtubePlayer == null) {
            initializeYoutube()
        } else {
            if (isYoutubePlayerOpen) {
                if (currentLink != link) {
                    youtubePlayer?.loadVideo(link)
                    currentLink = link
                } else {
                    layoutUtils.createToast(applicationContext, "Ya estás reproduciendo este video")
                }
            } else {
                TransitionManager.beginDelayedTransition(container)
                main_toolbar.visibility = View.INVISIBLE
                player_background.minHeight = layoutUtils.toPx(240)
                youtubePlayer?.loadVideo(link)
            }
        }
        isYoutubePlayerOpen = true
    }

    private fun initializeYoutube() {
        if (!isYoutubeInitialized) {
            isYoutubeInitialized = true

            TransitionManager.beginDelayedTransition(container)
            main_toolbar.visibility = View.INVISIBLE
            player_background.minHeight = layoutUtils.toPx(240)

            val youtubePlayerSupportFragment = YouTubePlayerSupportFragmentX.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.main_player, youtubePlayerSupportFragment).commit()
            youtubePlayerSupportFragment.initialize(
                resources.getString(R.string.API_KEY),
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(
                        p0: YouTubePlayer.Provider?,
                        p1: YouTubePlayer?,
                        p2: Boolean
                    ) {
                        p1?.loadVideo(incomingLink)
                        currentLink = incomingLink
                        youtubePlayer = p1
                    }

                    override fun onInitializationFailure(
                        p0: YouTubePlayer.Provider?,
                        p1: YouTubeInitializationResult?
                    ) {
                        layoutUtils.createToast(
                            applicationContext,
                            "Error al iniciar Youtube"
                        )
                    }
                })
        }
    }

    override fun currentlyLoading() {
        main_progressbar.visibility = View.VISIBLE
    }

    override fun finishedLoading() {
        main_progressbar.visibility = View.INVISIBLE
    }

    override fun toolbarOff() {
        main_toolbar.visibility = View.INVISIBLE
    }

    override fun toolbarOn() {
        main_toolbar.visibility = View.VISIBLE
    }
}

interface IProgressBar {
    fun currentlyLoading()
    fun finishedLoading()
}

interface IYoutubePlayer {
    fun openYoutubePlayer(link: String)
}

interface IToolbar {
    fun toolbarOff()
    fun toolbarOn()
}
