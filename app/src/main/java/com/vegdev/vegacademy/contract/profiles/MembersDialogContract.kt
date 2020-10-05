package com.vegdev.vegacademy.contract.profiles

import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.presenter.profiles.members.OrgMembersViewHolder

interface MembersDialogContract {

    interface View {
        fun buildRv(adapter: RecyclerView.Adapter<OrgMembersViewHolder>)
        fun getItemValue(i: Int): String?
        fun getEdtxtText(i: Int): String?
    }

    interface Actions {
        fun buildRv()
        fun bindMember(holder: OrgMembersViewHolder, position: Int)
        fun saveMembersChanges()
    }
}