package com.vegdev.vegacademy.presenter.login

import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.contract.login.LoginContract
import com.vegdev.vegacademy.model.data.dataholders.UserDataHolder
import com.vegdev.vegacademy.utils.Utils
import com.vegdev.vegacademy.view.main.MainActivity

class WelcomePresenter(
    private val context: Context,
    private val iView: LoginContract.View.Welcome
) : LoginContract.Actions.Welcome {

    override fun setUserName() {
        val text =
            "Hola, " + Utils.getFirstWord(FirebaseAuth.getInstance().currentUser?.displayName)
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