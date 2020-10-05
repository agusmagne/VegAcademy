package com.vegdev.vegacademy.contract.profiles

import com.google.android.gms.tasks.Task
import com.vegdev.vegacademy.model.data.models.users.Org

interface ProfileOrgContract {

    interface View {
        fun enterExitEditMode(enter: Boolean)
        fun resetValues(description: String, location: String, showMembers: Boolean, showContact: Boolean)
        fun enableDisableBtns(showMembers: Boolean, showContact: Boolean)
    }

    interface Actions {
        fun discardChanges()
        fun saveChanges(
            description: String,
            location: String,
            showMembers: Boolean,
            showContact: Boolean
        )

        fun enableDisableBtns()
    }

    interface Data {
        fun updateOrg(org: Org?): Task<Void>
    }

}