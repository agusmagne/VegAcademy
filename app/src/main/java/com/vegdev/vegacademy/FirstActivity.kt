package com.vegdev.vegacademy

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.AttributeSet
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.util.Pair
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.Utils.LayoutUtils
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        firebaseAuth = FirebaseAuth.getInstance()

        logo.setOnClickListener {

        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val act: Context = this
            val animation = AnimationUtils.loadAnimation(this, R.anim.welcome_fade_out)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                im_signedin,
                "logo"
            ).toBundle()
            animation.startOffset = 1000
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    rl_unsigned.visibility = View.GONE


                    val intent = Intent(act, WelcomeActivity::class.java)
                    act.startActivity(intent, options)
                }

            })
            rl_unsigned.startAnimation(animation)
        } else {

            val p1 = android.util.Pair.create(logo as View, "logo")
            val options =
                ActivityOptions.makeSceneTransitionAnimation(this, p1).toBundle()
            val intent = Intent(this, StartActivity::class.java)
            this.startActivity(intent, options)
        }
    }


}
