package com.vegdev.vegacademy.presenter.profiles.members

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.profiles.MembersListContract
import com.vegdev.vegacademy.helpers.utils.Utils
import com.vegdev.vegacademy.model.data.models.users.User
import com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.spinner.SpinnerAdapter

class OrgMembersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    MembersListContract.View, AdapterView.OnItemSelectedListener {

    private var root: ViewGroup? = itemView.findViewById(R.id.members_single_root)
    private var nameTxt: TextView? = itemView.findViewById(R.id.member_name_txt)
    private var emailTxt: TextView? = itemView.findViewById(R.id.member_email_txt)
    private var roleSpinner: Spinner? = itemView.findViewById(R.id.member_role_spinner)
    private var roleEdtxt: EditText? = itemView.findViewById(R.id.member_role_edtxt)

    override fun bindMemberView(member: User, adapter: SpinnerAdapter) {
        nameTxt?.text = member.username
        emailTxt?.text = member.email
        roleSpinner?.adapter = adapter
        roleSpinner?.onItemSelectedListener = this
    }

    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
        adapter?.let {
            if (position == roleSpinner?.count!! - 1) {
                showHideRoleEditText(View.VISIBLE)
            } else {
                showHideRoleEditText(View.GONE)
            }
        }
    }

    override fun onNothingSelected(adapter: AdapterView<*>?) {}

    private fun showHideRoleEditText(visibility: Int) {
        delayedTransition()
        when (visibility) {
            View.VISIBLE -> {
                roleEdtxt?.visibility = View.VISIBLE
            }
            View.GONE -> {
                roleEdtxt?.editableText?.clear()
                roleEdtxt?.visibility = View.GONE
            }
        }
    }

    private fun delayedTransition() {
        root?.let { TransitionManager.beginDelayedTransition(it.parent as ViewGroup) }
    }
}