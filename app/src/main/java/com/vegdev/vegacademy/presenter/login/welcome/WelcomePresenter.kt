package com.vegdev.vegacademy.presenter.login.welcome

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.login.welcome.WelcomeView
import com.vegdev.vegacademy.view.main.main.MainActivity

class WelcomePresenter(val context: Context, val view: WelcomeView) {

    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val layoutUtils = LayoutUtils()

    fun setUserName() {
        val text =
            "Hola, " + firebaseUser?.displayName?.split(" ", ignoreCase = true, limit = 2)?.first()
        view.setText(text)
    }

    fun shouldExit(intent: Intent?) {
        intent?.let {
            if (it.getBooleanExtra("EXIT", false)) {
                view.onBackPressed()
            }
        }
    }

    fun startMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        layoutUtils.overrideEnterAndExitTransitions(context as Activity)
    }
}