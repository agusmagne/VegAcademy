package com.vegdev.vegacademy.presenter.login

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.contract.login.LoginContract
import com.vegdev.vegacademy.model.data.dataholders.UserDataHolder
import com.vegdev.vegacademy.model.data.models.users.User

class CreateOrgPresenter(private val iView: LoginContract.View.CreateOrg) :
    LoginContract.Actions.CreateOrg {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun createOrgIntent(
        name: String,
        email: String,
        password: String,
        confPassword: String
    ) {
        if (name.isBlank() || email.isBlank() || password.isBlank() || confPassword.isBlank()) {
            iView.makeToast("Todos los campos son necesarios")
            return
        }
        if (!password.equals(confPassword, false)) {
            iView.makeToast("Las contraseñas no coinciden")
            return
        }

        iView.showProgressbar()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.let { firebaseUser ->
                    // update firebase user display name
                    firebaseUser.updateProfile(
                        UserProfileChangeRequest.Builder().setDisplayName(name).build()
                    ).addOnSuccessListener {
                        FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(firebaseUser.uid)
                            .set(createOrganization(firebaseUser.uid, name, email))

                        iView.hideProgressbar()
                        iView.startWelcomeActivity()
                    }

                }
            }
    }

    private fun createOrganization(id: String, name: String, email: String): User {
        val newOrg = User()
        newOrg.id = id
        newOrg.username = name
        newOrg.email = email
        newOrg.isOrg = true

        UserDataHolder.setUser(newOrg)

        return newOrg
    }
}