package com.vegdev.vegacademy.presenter.login.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.login.login.LoginView
import com.vegdev.vegacademy.view.login.welcome.WelcomeActivity

class LoginPresenter(val context: Context, val view: LoginView) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val layoutUtils = LayoutUtils()

    fun signInIntent(email: String, password: String) {
        if (email != "" && password != "") {

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                view.beginDelayedTransition()

                val intent = Intent(context, WelcomeActivity::class.java)
                context.startActivity(intent)
                layoutUtils.overrideEnterAndExitTransitions(context as Activity)


            }.addOnFailureListener {

                layoutUtils.createToast(context, "Error al iniciar sesión")
            }
        } else {
            layoutUtils.createToast(context, "Debe ingresar su correo y contraseña")

        }
    }
}