package com.vegdev.vegacademy.view.profiles

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.contract.profiles.MembersDialogContract
import com.vegdev.vegacademy.presenter.profiles.members.MembersDialogPresenter
import com.vegdev.vegacademy.presenter.profiles.members.OrgMembersViewHolder
import com.vegdev.vegacademy.utils.Utils

class MembersDialogFragment : DialogFragment(), MembersDialogContract.View {

    private var presenter: MembersDialogPresenter? = null

    private var membersRv: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_members_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
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

    override fun onResume() {
        super.onResume()

        // set height and width
        val width = resources.displayMetrics.widthPixels - Utils.toPx(120)
        val height = resources.displayMetrics.heightPixels - Utils.toPx(360)
        val params = dialog?.window?.attributes
        params?.width = width; params?.height = height
        dialog?.window?.attributes = params

        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_add_recipe)
    }

    private fun bindViews(view: View) {
        membersRv = view.findViewById(R.id.members_rv)
    }
}