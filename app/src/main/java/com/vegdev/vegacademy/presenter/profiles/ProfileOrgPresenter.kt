package com.vegdev.vegacademy.presenter.profiles

import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.contract.profiles.ProfileOrgContract
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

    override fun saveChanges(description: String?, location: String?) {
        iMainView.hideKeyboard()
        iMainView.makeToast("Actualizando tu organización")
        iView.exitEditMode()
        iView.updateOrg(description, location)
        repository.updateOrg(description, location).addOnSuccessListener {
            iMainView.makeToast("¡Organización actualizada!")
        }
    }

}