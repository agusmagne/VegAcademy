package com.vegdev.vegacademy.contract.profiles

import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.presenter.profiles.members.OrgMembersViewHolder

interface MembersDialogContract {

    interface View {
        fun buildRv(adapter: RecyclerView.Adapter<OrgMembersViewHolder>)
    }

    interface Actions {
        fun buildRv()
    }
}