package com.vegdev.vegacademy.presenter.login

import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.contract.login.LoginContract
import com.vegdev.vegacademy.view.main.MainActivity

class WelcomePresenter(
    private val context: Context,
    private val iView: LoginContract.View.Welcome
) : LoginContract.Actions.Welcome {

    private val firebaseUser = FirebaseAuth.getInstance().currentUser

    override fun setUserName() {
        val text =
            "Hola, " + firebaseUser?.displayName?.split(" ", ignoreCase = true, limit = 2)?.first()
        iView.setText(text)
    }

    override fun shouldExit(intent: Intent?) {
        intent?.let {
            if (it.getBooleanExtra("EXIT", false)) {
                iView.onBackPressed()
            }
        }
    }

    override fun startMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}