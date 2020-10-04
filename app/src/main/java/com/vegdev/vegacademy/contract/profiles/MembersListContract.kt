package com.vegdev.vegacademy.contract.profiles

import android.widget.AdapterView
import com.vegdev.vegacademy.model.data.models.users.User
import com.vegdev.vegacademy.presenter.profiles.members.OrgMembersViewHolder
import com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.spinner.SpinnerAdapter

interface MembersListContract {

    interface View {
        fun bindMemberView(member: User, adapter: SpinnerAdapter)
    }

    interface Actions {
        fun bindMember(holder: OrgMembersViewHolder, position: Int)

    }
}