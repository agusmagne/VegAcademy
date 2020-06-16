package com.vegdev.vegacademy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.Utils.LayoutUtils
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUserActivity : AppCompatActivity() {

    val layoutUtils = LayoutUtils()
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        firebaseAuth = FirebaseAuth.getInstance()

        back_btn.setOnClickListener {
            onBackPressed()
        }

        start_create_user_filled_btn.setOnClickListener {

            val name = name_txt.text.toString()
            val email = email_txt.text.toString()
            val password = password_txt.text.toString()
            val confPassword = confpassword_txt.text.toString()

            if (password != confPassword) {
                layoutUtils.createToast(this, "Las contraseñas no coinciden")
                confpassword_field.setImageResource(R.drawable.redroundborder40dp)

            } else {


                val animation = TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
                )
                animation.duration = 500

                progress_bar.startAnimation(animation)
                progress_bar_background.startAnimation(animation)
                progress_bar.visibility = View.VISIBLE
                progress_bar_background.visibility = View.VISIBLE

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    val intent = Intent(this, WelcomeActivity::class.java)
                    this.startActivity(intent)
                }.addOnFailureListener {

                }
            }

        }

    }

}
