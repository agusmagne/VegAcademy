package com.vegdev.vegacademy.presenter.login.first

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.view.login.first.FirstView
import com.vegdev.vegacademy.view.login.start.StartActivity
import com.vegdev.vegacademy.view.login.welcome.WelcomeActivity

class FirstPresenter(val context: Context, val activity: Activity, val view: FirstView) {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun onInitAnimate() {
        // if user is logged in go to WelcomeActivity
        // if user's not logged in go StartActivity
        if (firebaseAuth.currentUser != null) {

            val animation = animateLogo()
            view.animateRlUnsigned(animation)
        } else {

            val handler = Handler()
            handler.postDelayed({
                val p1 = android.util.Pair.create(view.getLogo(), "logo")
                val options =
                    ActivityOptions.makeSceneTransitionAnimation(activity, p1)
                val intent = Intent(context, StartActivity::class.java)
                context.startActivity(intent, options.toBundle())
            }, 1000)
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
                val options = ActivityOptions.makeSceneTransitionAnimation(activity, p1)
                context.startActivity(intent, options.toBundle())
            }
        })
        return animation
    }
}