package com.vegdev.vegacademy.contract.profiles

import android.widget.AdapterView
import com.vegdev.vegacademy.model.data.models.users.Member
import com.vegdev.vegacademy.presenter.profiles.members.OrgMembersViewHolder
import com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.spinner.SpinnerAdapter

interface MembersListContract {

    interface View {
        fun bindMemberView(
            member: Member?,
            spinnerPosition: Int,
            adapter: SpinnerAdapter,
            onItemSelectedListener: AdapterView.OnItemSelectedListener,
            onTextChange: (String?) -> Unit
        )
        fun getSpinnerItemPosition(): Int?
        fun getSpinnerItemValue(): String?
    }

    interface Actions {
        fun bindMember(holder: OrgMembersViewHolder, position: Int)

    }
}