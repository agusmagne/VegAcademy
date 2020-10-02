package com.vegdev.vegacademy.presenter.login

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.login.LoginContract
import com.vegdev.vegacademy.view.login.StartActivity
import com.vegdev.vegacademy.view.login.WelcomeActivity

class FirstPresenter(val context: Context, val view: LoginContract.View.First) :
    LoginContract.Actions.First {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onInitAnimate() {
        // if user is logged in go to WelcomeActivity
        // if user's not logged in go StartActivity
        if (firebaseAuth.currentUser != null) {

            val animation = animateLogo()
            view.animateRlUnsigned(animation)
        } else {

            val p1 = android.util.Pair.create(view.getLogo(), "logo")
            val options =
                ActivityOptions.makeSceneTransitionAnimation((context as Activity), p1)
            val intent = Intent(context, StartActivity::class.java)
            context.startActivity(intent, options.toBundle())
        }
    }

    private fun animateLogo(): Animation {
        val animation = AnimationUtils.loadAnimation(
            context,
            R.anim.welcome_fade_out
        )
        val p1 = android.util.Pair.create(view.getLogoIn(), "logo")
        animation.startOffset = 1000
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                view.hideRlUnsigned()
                val intent = Intent(context, WelcomeActivity::class.java)
                val options =
                    ActivityOptions.makeSceneTransitionAnimation((context as Activity), p1)
                context.startActivity(intent, options.toBundle())
            }
        })
        return animation
    }
}