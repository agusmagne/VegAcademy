package com.vegdev.vegacademy.presenter.login.createuser

import android.content.Context
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vegdev.vegacademy.model.data.models.User
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.login.createuser.CreateUserView
import com.vegdev.vegacademy.view.login.welcome.WelcomeActivity

class CreateUserPresenter(val context: Context, val view: CreateUserView) {

    private val layoutUtils = LayoutUtils()
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun createUserIntent(name: String, email: String, password: String, confPassword: String) {

        if (password != confPassword) {
            layoutUtils.createToast(context, "Las contraseñas no coinciden")
            view.changePasswordImageColor()

        } else {

            val animation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
            )
            animation.duration = 500
            view.startProgressBarAnimation(animation)

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { auth ->
                    auth.user?.updateProfile(
                        UserProfileChangeRequest.Builder().setDisplayName(name).build()
                    )?.addOnSuccessListener {
                        Firebase.firestore.collection("users").document(auth.user?.uid!!)
                            .set(User())
                        val intent = Intent(context, WelcomeActivity::class.java)
                        context.startActivity(intent)
                    }
                }.addOnFailureListener {
                }
        }
    }
}