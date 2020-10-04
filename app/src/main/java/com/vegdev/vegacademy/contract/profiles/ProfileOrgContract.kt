package com.vegdev.vegacademy.contract.profiles

import com.google.android.gms.tasks.Task
import com.vegdev.vegacademy.model.data.models.users.Org

interface ProfileOrgContract {

    interface View {
        fun enterExitEditMode(enter: Boolean)
        fun resetValues()
        fun updateOrg(description: String?, location: String?)
        fun enableDisableBtns(showMembers: Boolean, showContact: Boolean)
    }

    interface Actions {
        fun discardChanges(description: String?, location: String?)
        fun saveChanges(
            org: Org,
            description: String?,
            location: String?,
            showMembers: Boolean,
            showContact: Boolean
        )

    }

    interface Data {
        fun updateOrg(org: Org): Task<Void>
    }

}