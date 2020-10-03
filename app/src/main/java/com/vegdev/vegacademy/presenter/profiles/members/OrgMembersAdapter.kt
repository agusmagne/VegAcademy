package com.vegdev.vegacademy.presenter.profiles.members

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.dataholders.UserDataHolder
import com.vegdev.vegacademy.model.data.models.users.User

class OrgMembersAdapter : RecyclerView.Adapter<OrgMembersViewHolder>() {

    private val members: MutableList<User> = UserDataHolder.currentUser.organization.members

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgMembersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.members_single, parent, false)
        return OrgMembersViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrgMembersViewHolder, position: Int) {
//        holder.bindView()
    }

    override fun getItemCount(): Int = members.size
}