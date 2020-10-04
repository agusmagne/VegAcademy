package com.vegdev.vegacademy.presenter.profiles.members

import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.contract.profiles.MembersDialogContract
import com.vegdev.vegacademy.view.profiles.MembersDialogFragment

class MembersDialogPresenter(private val iView: MembersDialogContract.View, private val iMainView: MainContract.View) : MembersDialogContract.Actions {

    override fun buildRv() {
        val adapter = OrgMembersAdapter()
        iView.buildRv(adapter)
    }


}