package com.vegdev.vegacademy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.login.StartActivity
import com.vegdev.vegacademy.login.WelcomeActivity
import com.vegdev.vegacademy.ui.news.NewsFragment
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IToolbar, IProgressBar, IYoutubePlayer {

    val layoutUtils = LayoutUtils()
    lateinit var firebaseAuth: FirebaseAuth
    private var youtubePlayer: YouTubePlayer? = null
    private var isYoutubePlayerOpen: Boolean = false
    private var isYoutubeInitialized: Boolean = false
    private var currentLink: String = ""
    private var incomingLink: String = ""
    private val listener: MotionLayout.TransitionListener =
        object : TransitionAdapter() {
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {

                if (!isYoutubeInitialized) {
                    isYoutubeInitialized = true
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
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_toolbar.visibility = View.VISIBLE

        firebaseAuth = FirebaseAuth.getInstance()

        setSupportActionBar(main_toolbar)
        supportActionBar?.title = ""

        val navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)
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

    override fun toolbarOff() {
        main_toolbar.visibility = View.GONE
    }

    override fun toolbarOn() {
        main_toolbar.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        youtubePlayer?.pause()
        currentLink = ""
        incomingLink = ""

        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val currentFragment =
            fragment?.childFragmentManager?.fragments?.get(0) as? IOnFragmentBackPressed

        if (currentFragment is NewsFragment) {
            currentFragment.onFragmentBackPressed().takeIf { !it }?.let {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra("EXIT", true)
                startActivity(intent)
            }
        } else {
            currentFragment?.onFragmentBackPressed()?.takeIf { !it }?.let {
                super.onBackPressed()
            }
        }
    }

    override fun loading() {
        main_progressbar.visibility = View.VISIBLE
    }

    override fun loaded() {
        main_progressbar.visibility = View.INVISIBLE
    }

    override fun openYoutubePlayer(link: String) {
        incomingLink = link
        if (youtubePlayer == null) {
            //container.addTransitionListener(listener)
            initializeYoutube()
        } else {
            if (currentLink != link) {
                youtubePlayer?.loadVideo(link)
                currentLink = link
            } else {
                layoutUtils.createToast(applicationContext, "Ya estás reproduciendo este video")
            }

        }
        container.transitionToState(R.id.openYoutubePlayer)
    }

    override fun closeYoutubePlayer() {
        container.transitionToState(R.id.closeYoutubePlayer)
    }

    override fun setYoutubePlayerState(isItOpen: Boolean) {
        isYoutubePlayerOpen = isItOpen
    }

    override fun getYoutubePlayerState(): Boolean {
        return isYoutubePlayerOpen
    }

    private fun initializeYoutube() {
        if (!isYoutubeInitialized) {
            isYoutubeInitialized = true
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
}


interface IProgressBar {
    fun loading()
    fun loaded()
}

interface IToolbar {
    fun toolbarOff()
    fun toolbarOn()
}

interface IYoutubePlayer {
    fun openYoutubePlayer(link: String)
    fun closeYoutubePlayer()
    fun setYoutubePlayerState(isItOpen: Boolean)
    fun getYoutubePlayerState(): Boolean
}

interface IOnFragmentBackPressed {
    fun onFragmentBackPressed(): Boolean
}
