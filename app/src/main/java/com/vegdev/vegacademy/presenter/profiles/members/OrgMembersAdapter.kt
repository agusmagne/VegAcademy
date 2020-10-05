package com.vegdev.vegacademy.presenter.profiles.members

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R

class OrgMembersAdapter(private val presenter: MembersDialogPresenter) : RecyclerView.Adapter<OrgMembersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgMembersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.members_single, parent, false)
        return OrgMembersViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: OrgMembersViewHolder, position: Int) {
        presenter.bindMember(holder, position)
    }

    override fun getItemCount(): Int = presenter.membersSize


}