package com.vegdev.vegacademy.presenter.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.login.LoginContract
import com.vegdev.vegacademy.utils.Utils
import com.vegdev.vegacademy.view.login.CreateUserActivity
import com.vegdev.vegacademy.view.login.LoginActivity
import com.vegdev.vegacademy.view.login.WelcomeActivity

class StartPresenter(val context: Context, val view: LoginContract.View.Start) :
    LoginContract.Actions.Start {

    private val RC_SIGN_IN = 1
    private val firebaseUser = FirebaseAuth.getInstance().currentUser
    private val layoutUtils = Utils()

    override fun createFacebookLoginIntent() {
        val providers = arrayListOf(AuthUI.IdpConfig.FacebookBuilder().build())
        (context as Activity).startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun buildAnimationAndStartCreateUserActivity() {
        val optionsBundle = view.onCreateUserClickBuildAnimationOptions()
        val animation = AnimationUtils.loadAnimation(context, R.anim.create_btn_fade_out)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                val intent = Intent(context, CreateUserActivity::class.java)
                context.startActivity(intent, optionsBundle)
            }
        })
        view.onCreateUserClickStartAnimations(animation)
    }

    override fun buildAnimationAndStartLoginActivity() {
        if (firebaseUser != null) {

            val intent = Intent(context, WelcomeActivity::class.java)
            context.startActivity(intent)
            layoutUtils.overrideEnterAndExitTransitions((context as Activity))

        } else {

            // user's not logged then start login activity
            val options = view.onLoginClickBuildAnimationOptions()
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent, options)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == RC_SIGN_IN) {
//            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val intent = Intent(context, WelcomeActivity::class.java)
                context.startActivity(intent)
                layoutUtils.overrideEnterAndExitTransitions((context as Activity))

            } else {
                val toast =
                    Toast.makeText(
                        context,
                        "Error al iniciar sesión con Facebook",
                        Toast.LENGTH_LONG
                    )
                toast.show()
            }
        }
    }

    override fun onRestartAnimateColor() {
        val animation = AnimationUtils.loadAnimation(
            context,
            R.anim.create_btn_fade_in
        )
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                view.changeButtonsAlpha()
            }
        })
        animation.startOffset = 550
        view.onCreateUserClickStartAnimations(animation)
    }
}