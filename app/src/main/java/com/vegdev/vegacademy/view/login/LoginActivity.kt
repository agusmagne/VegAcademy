package com.vegdev.vegacademy.view.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.TransitionManager
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val layoutUtils = LayoutUtils()
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        login_btn.setOnClickListener {

            if (email_txt.text.toString() != "" && password_txt.text.toString() != "") {
                val email = email_txt.text.toString().trim()
                val password = password_txt.text.toString().trim()

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                    TransitionManager.beginDelayedTransition(login_constraint)

                    val intent = Intent(this, WelcomeActivity::class.java)
                    this.startActivity(intent)
                    layoutUtils.overrideEnterAndExitTransitions(this)


                }.addOnFailureListener {

                    layoutUtils.createToast(this, "Error al iniciar sesión")
                }
            } else {
                layoutUtils.createToast(this, "Debe ingresar su correo y contraseña")

            }

        }
    }
}