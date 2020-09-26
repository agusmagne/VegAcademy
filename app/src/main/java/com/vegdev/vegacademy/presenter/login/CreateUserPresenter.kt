package com.vegdev.vegacademy.presenter.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.contract.login.LoginContract
import com.vegdev.vegacademy.model.data.models.User
import com.vegdev.vegacademy.utils.Utils
import com.vegdev.vegacademy.view.login.WelcomeActivity

class CreateUserPresenter(private val context: Context, private val iView: LoginContract.View.CreateUser) :
    LoginContract.Actions.CreateUser {

    private val layoutUtils = Utils()
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun createUserIntent(
        name: String,
        email: String,
        password: String,
        confPassword: String
    ) {

        if (name == "" || email == "" || password == "" || confPassword == "") {
            layoutUtils.createToast(context, "Todos los campos son necesarios")
            return
        }
        if (password != confPassword) {
            layoutUtils.createToast(context, "Las contraseñas no coinciden")
            return
        }

        iView.showProgressbar()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { auth ->
                auth.user?.updateProfile(
                    // update firebase user displayName
                    UserProfileChangeRequest.Builder().setDisplayName(name).build()
                )?.addOnSuccessListener {

                    // create empty user document
                    FirebaseFirestore.getInstance().collection("users").document(auth.user?.uid!!)
                        .set(User())
                    iView.hideProgressbar()
                    val intent = Intent(context, WelcomeActivity::class.java)
                    context.startActivity(intent)
                    layoutUtils.overrideEnterAndExitTransitions(context as Activity)
                }
            }.addOnFailureListener {
                iView.hideProgressbar()
                layoutUtils.createToast(context, "Error al crear usuario")
            }
    }

}