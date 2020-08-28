package com.vegdev.vegacademy.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.User
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUserActivity : AppCompatActivity() {

    private val layoutUtils = LayoutUtils()
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        firebaseAuth = FirebaseAuth.getInstance()

        back_btn.setOnClickListener {
            onBackPressed()
        }

        create_btn.setOnClickListener {

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

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { auth ->
                        auth.user?.updateProfile(
                            UserProfileChangeRequest.Builder().setDisplayName(name).build()
                        )?.addOnSuccessListener {
                            Firebase.firestore.collection("users").document(auth.user?.uid!!)
                                .set(User())
                            val intent = Intent(this, WelcomeActivity::class.java)
                            this.startActivity(intent)
                        }
                    }.addOnFailureListener {
                }
            }

        }

    }

}
