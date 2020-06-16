package com.vegdev.vegacademy

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.Utils.LayoutUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val layoutUtils = LayoutUtils()
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        back_btn.setOnClickListener {
            onBackPressed()
        }

        login_btn.setOnClickListener {

            if (email_txt.text.toString() != "" && password_txt.text.toString() != "") {



                val email = email_txt.text.toString().trim()
                val password = password_txt.text.toString().trim()

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                    val context: Context = this
                    val activity: Activity = this
                    val animation = TranslateAnimation(0f, 0f, 0f, 5000f)
                    animation.duration = 1000
                    animation.fillAfter = true
                    animation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(animation: Animation?) {}
                        override fun onAnimationStart(animation: Animation?) {}

                        override fun onAnimationEnd(animation: Animation?) {
                            val intent = Intent(context, WelcomeActivity::class.java)
                            context.startActivity(intent)
                            layoutUtils.overrideEnterAndExitTransitions(activity)
                        }

                    })
                    view1.startAnimation(animation)
                    view2.startAnimation(animation)
                    view3.startAnimation(animation)
                    view4.startAnimation(animation)
                    view5.startAnimation(animation)
                    email_field.startAnimation(animation)
                    email_txt.startAnimation(animation)
                    password_field.startAnimation(animation)
                    password_txt.startAnimation(animation)
                    login_btn.startAnimation(animation)
                    login_txt.startAnimation(animation)



                }.addOnFailureListener {

                    layoutUtils.createToast(this, "Error al iniciar sesión")
                }

            } else {
                layoutUtils.createToast(this, "Debe ingresar su correo y contraseña")
            }
        }
    }
}
