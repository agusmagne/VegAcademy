package com.vegdev.vegacademy.presenter.profiles

import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.contract.profiles.ProfileOrgContract
import com.vegdev.vegacademy.model.data.dataholders.UserDataHolder
import com.vegdev.vegacademy.model.data.models.users.Member
import com.vegdev.vegacademy.model.data.models.users.User
import com.vegdev.vegacademy.model.data.repositories.users.OrgRepository

class ProfileOrgPresenter(
    private val iView: ProfileOrgContract.View,
    private val iMainView: MainContract.View
) : ProfileOrgContract.Actions {

    private val repository = OrgRepository()
    private var org = UserDataHolder.currentUser.organization

    fun enterEditMode() {
        iView.enterExitEditMode(true)
    }

    override fun discardChanges() {
        org?.let { iView.resetValues(it.description, it.location, it.showMembers, it.showContact) }
        iView.enterExitEditMode(false)
        iMainView.hideKeyboard()
    }

    override fun saveChanges(
        description: String,
        location: String,
        showMembers: Boolean,
        showContact: Boolean
    ) {
        iMainView.hideKeyboard()
        iMainView.makeToast("Actualizando tu organización")
        iView.enterExitEditMode(false)

        iView.enableDisableBtns(showMembers, showContact)

        updateOrg(description, location, showMembers, showContact)
        repository.updateOrg(org).addOnSuccessListener {
            iMainView.makeToast("¡Organización actualizada!")
        }
    }

    override fun enableDisableBtns() {
        org?.let { iView.enableDisableBtns(it.showMembers, it.showContact) }
    }

    private fun updateOrg(
        description: String,
        location: String,
        showMembers: Boolean,
        showContact: Boolean
    ) {
        org?.description = description
        org?.location = location
        org?.showMembers = showMembers
        org?.showContact = showContact
    }
}