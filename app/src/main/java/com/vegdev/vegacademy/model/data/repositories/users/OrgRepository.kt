package com.vegdev.vegacademy.model.data.repositories.users

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.vegdev.vegacademy.contract.profiles.ProfileOrgContract
import com.vegdev.vegacademy.model.data.dataholders.UserDataHolder

class OrgRepository : ProfileOrgContract.Data {

    private val firestore = FirebaseFirestore.getInstance()

    override fun updateOrg(description: String?, location: String?) : Task<Void> {
        return firestore.collection("users").document(UserDataHolder.currentUser.id).update(mapOf(
            "organization.description" to description,
            "organization.location" to location
        ))
    }
}