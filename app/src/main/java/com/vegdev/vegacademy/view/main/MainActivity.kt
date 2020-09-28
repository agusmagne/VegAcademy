package com.vegdev.vegacademy.view.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.presenter.main.MainPresenter
import com.vegdev.vegacademy.view.login.WelcomeActivity
import com.vegdev.vegacademy.view.news.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private var presenter = MainPresenter(this, supportFragmentManager, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)
        nav_view.setOnNavigationItemReselectedListener {}

        lifecycleScope.launchWhenCreated {
            presenter.init()
        }

        setSupportActionBar(main_toolbar)
        supportActionBar?.title = ""


        // set FAB click listener allowing to close youtube player interface
        fab_closeyoutube.setOnClickListener {
            presenter.closeYouTubePlayer()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        menu?.findItem(R.id.action_addrecipe)?.isVisible = false
        menu?.findItem(R.id.action_filterrecipe)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                presenter.logOut()
            }
            R.id.action_addrecipe -> {
                val navOptions =
                    NavOptions.Builder().setEnterAnim(R.anim.fragment_in_slide_alpha).build()
                nav_host_fragment.findNavController()
                    .navigate(R.id.navigation_recipe_suggestion, null, navOptions, null)
            }
            R.id.action_donate -> {
                val navOptions =
                    NavOptions.Builder().setEnterAnim(R.anim.fragment_in_slide_alpha).build()
                nav_host_fragment.findNavController()
                    .navigate(R.id.navigation_donations, null, navOptions, null)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getCurrentFragment(): Fragment? =
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment)
            ?.childFragmentManager?.fragments?.get(0)

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()
        if (currentFragment is NewsFragment) {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("EXIT", true)
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }


    override fun navigateToDirection(direction: NavDirections) {
        findNavController(R.id.nav_host_fragment).navigate(direction)
    }

    override fun navigateWithOptions(
        navigationId: Int,
        bundle: Bundle,
        options: NavOptions?,
        extras: FragmentNavigator.Extras
    ) {
        findNavController(R.id.nav_host_fragment).navigate(navigationId, bundle, options, extras)
    }

    override fun onVideoClicked(url: String) {
        presenter.playVideo(url, fab_closeyoutube.isOrWillBeShown)
    }

    override fun openYoutubePlayer(minHeight: Int) {
        TransitionManager.beginDelayedTransition(container)
        player_background.minHeight = minHeight
        player.visibility = View.VISIBLE
        fab_closeyoutube.show()
    }

    override fun closeYoutubePlayer() {
        TransitionManager.beginDelayedTransition(container)
        player_background.minHeight = 0
        player.visibility = View.INVISIBLE
        fab_closeyoutube.hide()
    }

    override fun showNavView() {
        nav_view.visibility = View.VISIBLE
    }

    override fun hideNavView() {
        nav_view.visibility = View.INVISIBLE
    }

    override fun showProgress() {
        main_progressbar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        main_progressbar.visibility = View.INVISIBLE
    }

    override fun showWebProgressbar() {
        web_progressbar.visibility = View.VISIBLE
    }

    override fun hideWebProgressbar() {
        web_progressbar.visibility = View.INVISIBLE
    }

    override fun hideKeyboard() {
        val view = this.currentFocus
        view?.let {
            (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                it.windowToken,
                0
            )
        }


    }
}