package com.vegdev.vegacademy.presenter.profiles.members

import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.profiles.MembersListContract
import com.vegdev.vegacademy.helpers.enums.OrgMemberRolesEnum
import com.vegdev.vegacademy.model.data.dataholders.UserDataHolder
import com.vegdev.vegacademy.model.data.models.users.User
import com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.spinner.SpinnerAdapter

class MembersListPresenter : MembersListContract.Actions {

    private val members: MutableList<User> = UserDataHolder.currentUser.organization.members
    private val roles: MutableList<String> = OrgMemberRolesEnum.getAllRoles()
    var membersSize: Int = members.size

    override fun bindMember(holder: OrgMembersViewHolder, position: Int) {
        holder.bindMemberView(
            members[position],
            SpinnerAdapter(holder.itemView.context, R.layout.recipes_spinner_item, roles)
        )
    }
}