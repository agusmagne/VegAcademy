package com.vegdev.vegacademy.model.data.repositories.main

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainRepository : IMainRepository {

    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = Firebase.firestore

    override fun getFirebaseUser(): FirebaseUser? = firebaseAuth.currentUser
    override fun getUserInfo(path: String): Task<DocumentSnapshot> = firestore.document(path).get()


}