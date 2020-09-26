package com.vegdev.vegacademy.presenter.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.contract.login.LoginContract
import com.vegdev.vegacademy.utils.Utils
import com.vegdev.vegacademy.view.login.WelcomeActivity

class LoginPresenter(private val context: Context, private val iView: LoginContract.View.Login) :
    LoginContract.Actions.Login {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val layoutUtils = Utils()

    override fun signInIntent(email: String, password: String) {
        if (email != "" && password != "") {

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                iView.beginDelayedTransition()

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