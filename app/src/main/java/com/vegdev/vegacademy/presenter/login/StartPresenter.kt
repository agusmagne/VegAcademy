package com.vegdev.vegacademy.presenter.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.login.LoginContract
import com.vegdev.vegacademy.utils.Utils
import com.vegdev.vegacademy.view.login.CreateUserActivity
import com.vegdev.vegacademy.view.login.WelcomeActivity

class StartPresenter(val context: Context, val iView: LoginContract.View.Login) :
    LoginContract.Actions.Login {

    private val RC_SIGN_IN = 1
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser = firebaseAuth.currentUser
    private val layoutUtils = Utils()

    override fun createFacebookLoginIntent() {
        val providers = arrayListOf(AuthUI.IdpConfig.FacebookBuilder().build())
        (context as Activity).startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == RC_SIGN_IN) {
//            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val intent = Intent(context, WelcomeActivity::class.java)
                context.startActivity(intent)
                layoutUtils.overrideEnterAndExitTransitions((context as Activity))

            } else {
                val toast =
                    Toast.makeText(
                        context,
                        "Error al iniciar sesión con Facebook",
                        Toast.LENGTH_LONG
                    )
                toast.show()
            }
        }
    }

    override fun signInIntent(email: String, password: String) {
        if (email != "" && password != "") {

            iView.showProgress()
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                iView.hideProgress()

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

    override fun goToCreateUserActivity() {
        val options = iView.onCreateUserClickBuildAnimationOptions()
        val intent = Intent(context, CreateUserActivity::class.java)
        context.startActivity(intent, options)
    }
}