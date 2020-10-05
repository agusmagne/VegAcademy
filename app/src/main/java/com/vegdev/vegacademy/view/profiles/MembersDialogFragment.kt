package com.vegdev.vegacademy.view.profiles

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.contract.profiles.MembersDialogContract
import com.vegdev.vegacademy.helpers.utils.Utils
import com.vegdev.vegacademy.presenter.profiles.members.MembersDialogPresenter
import com.vegdev.vegacademy.presenter.profiles.members.OrgMembersViewHolder

class MembersDialogFragment : DialogFragment(), MembersDialogContract.View, View.OnClickListener {

    private var presenter: MembersDialogPresenter? = null
    private var membersRv: RecyclerView? = null
    private var saveChangesBtn: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_members_dialog, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        saveChangesBtn?.setOnTouchListener(Utils.getResizerOnTouchListener(saveChangesBtn))
        saveChangesBtn?.setOnClickListener(this)
        presenter?.buildRv()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainContract.View) presenter = MembersDialogPresenter(this, context)
    }

    override fun buildRv(adapter: RecyclerView.Adapter<OrgMembersViewHolder>) {
        membersRv.apply {
            this?.layoutManager = LinearLayoutManager(context)
            this?.adapter = adapter
        }
    }

    override fun getItemValue(i: Int): String? =
        membersRv?.getChildAt(i)?.findViewById<Spinner>(R.id.member_role_spinner)?.selectedItem?.toString()

    override fun getEdtxtText(i: Int): String? = membersRv?.getChildAt(i)?.findViewById<EditText>(R.id.member_role_edtxt)?.editableText?.toString()?.trim()


    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_add_recipe)
    }

    private fun bindViews(view: View) {
        membersRv = view.findViewById(R.id.members_rv)
        saveChangesBtn = view.findViewById(R.id.members_save_btn)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.members_save_btn -> presenter?.saveMembersChanges()
        }
    }
}