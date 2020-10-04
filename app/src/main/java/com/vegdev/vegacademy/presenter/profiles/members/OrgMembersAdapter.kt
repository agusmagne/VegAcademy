package com.vegdev.vegacademy.presenter.profiles.members

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R

class OrgMembersAdapter : RecyclerView.Adapter<OrgMembersViewHolder>() {

    private val presenter = MembersListPresenter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgMembersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.members_single, parent, false)
        return OrgMembersViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrgMembersViewHolder, position: Int) {
        presenter.bindMember(holder, position)
    }

    override fun getItemCount(): Int = presenter.membersSize
}