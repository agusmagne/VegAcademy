package com.vegdev.vegacademy

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.util.Pair
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        firebaseAuth = FirebaseAuth.getInstance()

    }

    override fun onStart() {
        super.onStart()

            // if user is logged in go to WelcomeActivity
            // if user's not logged in go StartActivity
            if (firebaseAuth.currentUser != null) {

                val animation = animateLogo()
                rl_unsigned.startAnimation(animation)
            } else {
                val act: Activity = this
                val cont: Context = this

                val handler = Handler()
                handler.postDelayed({
                    val p1 = android.util.Pair.create(logo as View, "logo")
                    val options =
                        ActivityOptions.makeSceneTransitionAnimation(act, p1)
                    val intent = Intent(cont, StartActivity::class.java)
                    cont.startActivity(intent, options.toBundle())
                }, 1000)


            }
    }

    fun animateLogo(): Animation {
        val cont: Context = this
        val act: Activity = this
        val animation = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_out)
        val p1 = android.util.Pair.create(logo_in as View, "logo")
        animation.startOffset = 1000
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                rl_unsigned.visibility = View.GONE
                val intent = Intent(cont, WelcomeActivity::class.java)
                val options = ActivityOptions.makeSceneTransitionAnimation(act, p1)
                cont.startActivity(intent, options.toBundle())
            }
        })
        return animation
    }
}