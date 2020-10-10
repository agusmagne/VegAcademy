package com.vegdev.vegacademy.presenter.profiles.members

import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.profiles.MembersListContract
import com.vegdev.vegacademy.model.data.models.users.Member
import com.vegdev.vegacademy.presenter.recipes.suggestion.adapter.spinner.SpinnerAdapter

class OrgMembersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    MembersListContract.View {

    private var root: ViewGroup? = itemView.findViewById(R.id.members_single_root)
    private var nameTxt: TextView? = itemView.findViewById(R.id.member_name_txt)
    private var emailTxt: TextView? = itemView.findViewById(R.id.member_email_txt)
    private var roleSpinner: Spinner? = itemView.findViewById(R.id.member_role_spinner)
    private var roleEdtxt: EditText? = itemView.findViewById(R.id.member_role_edtxt)
    private var visibilitySrc: ImageView? = itemView.findViewById(R.id.member_visibility_src)

    override fun bindMemberView(
        member: Member?,
        spinnerPosition: Int,
        adapter: SpinnerAdapter,
        onItemSelectedListener: AdapterView.OnItemSelectedListener,
        onTextChange: (String?) -> Unit
    ) {
        nameTxt?.text = member?.username
        emailTxt?.text = member?.email
        roleSpinner?.adapter = adapter
        roleSpinner?.onItemSelectedListener = onItemSelectedListener
        roleSpinner?.setSelection(spinnerPosition)
        setVisibilitySrc(member?.isVisible)
        roleEdtxt?.setText(member?.role)
        roleEdtxt?.doOnTextChanged { text, _, _, _ -> onTextChange(text?.toString()) }
    }

    override fun getSpinnerItemPosition(): Int? = roleSpinner?.selectedItemPosition

    override fun getSpinnerItemValue(): String? = roleSpinner?.selectedItem as String

    private fun setVisibilitySrc(isVisible: Boolean?) {
        isVisible?.let {
            if (!it) {
                visibilitySrc?.setImageResource(R.drawable.ic_visibility_off)
            }
        }
    }

    fun showHideRoleEditText(visibility: Int) {
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