package com.vegdev.vegacademy.model.data.repositories.main

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot

interface IMainRepository {

    fun getFirebaseUser(): FirebaseUser?
    fun getUserInfo(path: String): Task<DocumentSnapshot>
    fun getSuggestionQuery(): CollectionReference

}