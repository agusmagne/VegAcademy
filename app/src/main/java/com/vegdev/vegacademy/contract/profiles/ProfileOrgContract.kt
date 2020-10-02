package com.vegdev.vegacademy.contract.profiles

import com.google.android.gms.tasks.Task
import com.vegdev.vegacademy.model.data.models.users.Org

interface ProfileOrgContract {

    interface View {
        fun enterEditMode()
        fun exitEditMode()
        fun setEditTexts(description: String?, location: String?)
        fun updateOrg(description: String?, location: String?)
    }

    interface Actions {
        fun discardChanges(description: String?, location: String?)
        fun saveChanges(description: String?, location: String?)

    }

    interface Data {
        fun updateOrg(description: String?, location: String?): Task<Void>
    }

}