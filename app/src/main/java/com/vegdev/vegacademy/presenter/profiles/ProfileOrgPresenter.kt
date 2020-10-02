package com.vegdev.vegacademy.presenter.profiles

import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.contract.profiles.ProfileOrgContract
import com.vegdev.vegacademy.model.data.models.users.Org
import com.vegdev.vegacademy.model.data.repositories.users.OrgRepository

class ProfileOrgPresenter(private val iView: ProfileOrgContract.View, private val iMainView: MainContract.View) : ProfileOrgContract.Actions {

    private val repository = OrgRepository()

    fun enterEditMode() {
        iView.enterEditMode()
    }

    override fun discardChanges(description: String?, location: String?) {
        iView.setEditTexts(description, location)
        iView.exitEditMode()
        iMainView.hideKeyboard()
    }

    override fun saveChanges(
        org: Org,
        description: String?,
        location: String?,
        showMembers: Boolean,
        showContact: Boolean
    ) {
        iMainView.hideKeyboard()
        iMainView.makeToast("Actualizando tu organización")
        iView.exitEditMode()

        iView.enableDisableBtns(showMembers, showContact)

        description?.let { org.description = it }
        location?.let { org.location = it }
        org.showMembers = showMembers
        org.showContact = showContact

        iView.updateOrg(description, location)
        repository.updateOrg(org).addOnSuccessListener {
            iMainView.makeToast("¡Organización actualizada!")
        }
    }
}