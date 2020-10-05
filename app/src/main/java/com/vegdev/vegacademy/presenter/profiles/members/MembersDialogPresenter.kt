package com.vegdev.vegacademy.presenter.profiles.members

import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.contract.profiles.MembersDialogContract
import com.vegdev.vegacademy.helpers.enums.MemberRolesEnum
import com.vegdev.vegacademy.model.data.dataholders.UserDataHolder
import com.vegdev.vegacademy.model.data.models.users.Member
import com.vegdev.vegacademy.model.data.repositories.users.OrgRepository
import com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.spinner.SpinnerAdapter

class MembersDialogPresenter(
    private val iView: MembersDialogContract.View,
    private val iMainView: MainContract.View
) : MembersDialogContract.Actions {

    private val repository = OrgRepository()
    private val members: MutableList<Member>? = UserDataHolder.currentUser.organization?.members
    private val roles: MutableList<MemberRolesEnum> = MemberRolesEnum.getAllRoles()
    var membersSize: Int = members?.size ?: 0

    override fun buildRv() {
        val adapter = OrgMembersAdapter(this)
        iView.buildRv(adapter)
    }

    override fun bindMember(holder: OrgMembersViewHolder, position: Int) {
        val rolesValue = roles.map { it.value }
        val member = members?.get(position)
        val rolePosition = MemberRolesEnum.getRoleFromValue(member?.role).position
        holder.bindMemberView(
            member,
            rolePosition,
            SpinnerAdapter(holder.itemView.context, R.layout.recipes_spinner_item, rolesValue),
            spinnerOnItemSelectedListener(holder, position)
        ) { text -> onTextChange(text, position) }
    }

    private fun onTextChange(text: String?, position: Int) {
        members?.get(position)?.role = text?.trim()
    }

    override fun saveMembersChanges() {
        for (i in 0 until membersSize) {
            val itemValue = iView.getItemValue(i)
            if (itemValue == MemberRolesEnum.OTHER.value) {
                members?.get(i)?.role = iView.getEdtxtText(i)
            } else {
                members?.get(i)?.role = itemValue
            }
        }
        repository.updateOrg(UserDataHolder.currentUser.organization)
    }

    private fun spinnerOnItemSelectedListener(
        holder: OrgMembersViewHolder,
        memberPosition: Int
    ): AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                members?.get(memberPosition)?.role =
                    MemberRolesEnum.getRoleFromPosition(position).value
                if (position == roles.size - 1) {
                    holder.showHideRoleEditText(View.VISIBLE)
                } else {
                    holder.showHideRoleEditText(View.GONE)
                }
            }

            override fun onNothingSelected(adapter: AdapterView<*>?) {}
        }

    private fun editTextOnKeyChangeListener(memberPosition: Int): View.OnKeyListener =
        object : View.OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                TODO("Not yet implemented")
            }

        }
}