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
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.Utils.LayoutUtils
import com.vegdev.vegacademy.login.StartActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IToogleToolbar, IProgressBar {

    val layoutUtils = LayoutUtils()
    lateinit var firebaseAuth: FirebaseAuth

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
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val currentFragment = fragment?.childFragmentManager?.fragments?.get(0) as? IOnFragmentBackPressed
        currentFragment?.onFragmentBackPressed()?.takeIf { !it }?.let {
            super.onBackPressed()
        }
    }

    override fun loading() {
        main_progressbar.visibility = View.VISIBLE
    }

    override fun loaded() {
        main_progressbar.visibility = View.INVISIBLE
    }
}

interface IProgressBar {
    fun loading()
    fun loaded()
}

interface IToogleToolbar {
    fun toolbarOff()
    fun toolbarOn()
}

interface IOnFragmentBackPressed {
    fun onFragmentBackPressed(): Boolean
}