package com.vegdev.vegacademy.model.data.dataholders

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.model.data.models.User
import kotlinx.coroutines.tasks.await

object UserDataHolder {

    private var initialized: Boolean = false

    var firstName: String = ""
    var lastName: String = ""
    var likedRecipesId: MutableList<String> = mutableListOf()

    suspend fun getUserData(): User? {
        return if (!initialized) {
            initialized = true
            val user = fetchUser()
            user?.let {
                firstName = it.firstName
                lastName = it.lastName
                likedRecipesId = it.likedRecipesId
            }
            user
        } else {
            User(firstName, lastName, likedRecipesId)
        }
    }

    private suspend fun fetchUser(): User? {
        FirebaseAuth.getInstance().currentUser?.let {
            val uid = it.uid
            return FirebaseFirestore.getInstance().collection("users").document(uid).get().await().toObject(User::class.java)
        }
        return User()
    }


}