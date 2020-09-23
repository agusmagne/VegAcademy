package com.vegdev.vegacademy.view.main.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.User
import com.vegdev.vegacademy.model.domain.interactor.main.main.MainInteractor
import com.vegdev.vegacademy.presenter.main.main.MainPresenter
import com.vegdev.vegacademy.view.login.welcome.WelcomeActivity
import com.vegdev.vegacademy.view.news.news.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var userInfo: User
    private var presenter = MainPresenter(this, supportFragmentManager, this, MainInteractor())

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

        // search recipes toolbar logic
        edtxt_recipes_search.setOnEditorActionListener { textView, actionId, keyEvent ->
            false
        }

        btn_recipe_search.setOnClickListener {
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
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment)?.childFragmentManager?.fragments?.get(
            0
        )

    override fun navigateToDirection(direction: NavDirections) {
        findNavController(R.id.nav_host_fragment).navigate(direction)
    }

    override fun getUserInfo(): User = userInfo

    override fun setUserInfo(userInfo: User) {
        this.userInfo = userInfo
    }

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

    override fun showWebProgressbar() {
        web_progressbar.visibility = View.VISIBLE
    }

    override fun hideWebProgressbar() {
        web_progressbar.visibility = View.INVISIBLE
    }

    override fun onVideoClicked(url: String) {
        presenter.playVideo(url, fab_closeyoutube.isOrWillBeShown)
    }

    override fun showFAB() {
        fab_closeyoutube.show()
    }

    override fun hideFAB() {
        fab_closeyoutube.hide()
    }

    override fun transitionBackgroundToHeight(minHeight: Int) {
        TransitionManager.beginDelayedTransition(container)
        player_background.minHeight = minHeight
    }

    override fun showPlayer() {
        player.visibility = View.VISIBLE
    }

    override fun hidePlayer() {
        player.visibility = View.INVISIBLE
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
}