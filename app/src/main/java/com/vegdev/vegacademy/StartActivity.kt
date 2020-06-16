package com.vegdev.vegacademy

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair as UtilPair
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.Utils.LayoutUtils
import kotlinx.android.synthetic.main.activity_create_user.logo
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.activity_start.login_btn
import kotlinx.android.synthetic.main.activity_start.login_txt

class StartActivity : AppCompatActivity() {

    private val layoutUtils = LayoutUtils()
    private lateinit var firebaseAuth: FirebaseAuth
    private val RC_SIGN_IN = 1

    fun createSignInIntent() {
        val providers = arrayListOf(AuthUI.IdpConfig.FacebookBuilder().build())
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        firebaseAuth = FirebaseAuth.getInstance()

        fb_login_btn.setOnClickListener {
            createSignInIntent()
        }

        start_create_user_btn.setOnClickListener {

            val p1 = UtilPair.create(start_create_user_filled_btn as View, "image_slides_down")
            val p2 = UtilPair.create(start_create_user_filled_txt as View, "text_slides_down")
            val p3 = UtilPair.create(logo as View, "logo")
            val options =
                ActivityOptions.makeSceneTransitionAnimation(this, p1, p2, p3).toBundle()
            val context: Context = this

            val animation = AnimationUtils.loadAnimation(this, R.anim.create_btn_fade_out)
            start_create_user_btn.startAnimation(animation)
            start_create_user_txt.startAnimation(animation)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    val intent = Intent(context, CreateUserActivity::class.java)
                    context.startActivity(intent, options)
                }

                override fun onAnimationStart(animation: Animation?) {}
            })
        }

        login_txt.setOnClickListener {

            // check if user is already logged in
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                val intent = Intent(this, WelcomeActivity::class.java)
                this.startActivity(intent)
                layoutUtils.overrideEnterAndExitTransitions(this)
            } else {

                // user's not logged then start login activity
                val p1 = UtilPair.create(login_txt as View, "login_text")
                val p2 = UtilPair.create(login_btn as View, "login_image")
                val p3 = UtilPair.create(logo as View, "logo")

                val options =
                    ActivityOptions.makeSceneTransitionAnimation(this, p1, p2, p3).toBundle()

                val intent = Intent(this, LoginActivity::class.java)
                this.startActivity(intent, options)
            }
        }

        anonlogin_btn.setOnClickListener {
            layoutUtils.startActivity(this, WelcomeActivity::class.java)
            layoutUtils.overrideEnterAndExitTransitions(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val intent = Intent(this, WelcomeActivity::class.java)
                this.startActivity(intent)
                layoutUtils.overrideEnterAndExitTransitions(this)

            } else {
                val toast =
                    Toast.makeText(this, "Error al iniciar sesión con Facebook", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }


    override fun onRestart() {
        super.onRestart()

        val animation = AnimationUtils.loadAnimation(this, R.anim.create_btn_fade_in)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                start_create_user_btn.alpha = 1f
                start_create_user_txt.alpha = 1f

            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        animation.startOffset = 550
        start_create_user_btn.startAnimation(animation)
        start_create_user_txt.startAnimation(animation)
    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }

}
