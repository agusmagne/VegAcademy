package com.vegdev.vegacademy.view.profiles

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.main.MainContract
import com.vegdev.vegacademy.contract.profiles.ProfileOrgContract
import com.vegdev.vegacademy.model.data.dataholders.UserDataHolder
import com.vegdev.vegacademy.presenter.profiles.ProfileOrgPresenter
import com.vegdev.vegacademy.utils.Utils

class ProfileOrgFragment : Fragment(), ProfileOrgContract.View {

    private var org = UserDataHolder.currentUser.organization

    private var rootLayout: ViewGroup? = null
    private var enterEditModeBtn: Button? = null
    private var cancelEditModeBtn: Button? = null
    private var saveEditModeBtn: Button? = null
    private var descriptionEdtxt: EditText? = null
    private var locationEdtxt: EditText? = null
    private var membersBtn: Button? = null
    private var contactBtn: Button? = null

    private var presenter: ProfileOrgPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_org, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setButtonResizers()
        setClickListeners()
        exitEditMode()
        setEditTexts(org.description, org.location)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainContract.View) presenter = ProfileOrgPresenter(this, context)
    }

    override fun enterEditMode() {
        changeButtonsVisibility(true)
        changeClickabilty(listOf(descriptionEdtxt, locationEdtxt), true)
    }

    override fun exitEditMode() {
        changeButtonsVisibility(false)
        changeClickabilty(listOf(descriptionEdtxt, locationEdtxt), false)
    }

    override fun setEditTexts(description: String?, location: String?) {
        descriptionEdtxt?.setText(description)
        locationEdtxt?.setText(location)
    }

    override fun updateOrg(description: String?, location: String?) {
        description?.let {
            org.description = it
            UserDataHolder.currentUser.organization.description = it
        }
        location?.let {
            org.location = it
            UserDataHolder.currentUser.organization.location = it
        }

    }

    private fun bindViews(view: View) {
        rootLayout = view.findViewById(R.id.profile_org_root)
        enterEditModeBtn = view.findViewById(R.id.edit_mode_btn)
        cancelEditModeBtn = view.findViewById(R.id.cancel_edit_btn)
        saveEditModeBtn = view.findViewById(R.id.save_edit_btn)
        descriptionEdtxt = view.findViewById(R.id.desc)
        locationEdtxt = view.findViewById(R.id.location_txt)
        membersBtn = view.findViewById(R.id.members_btn)
        contactBtn = view.findViewById(R.id.contact_btn)
    }

    private fun changeButtonsVisibility(editMode: Boolean) {
        rootLayout?.let { TransitionManager.beginDelayedTransition(it) }
        if (editMode) {
            enterEditModeBtn?.visibility = View.INVISIBLE
            cancelEditModeBtn?.visibility = View.VISIBLE
            saveEditModeBtn?.visibility = View.VISIBLE
        } else {
            enterEditModeBtn?.visibility = View.VISIBLE
            cancelEditModeBtn?.visibility = View.INVISIBLE
            saveEditModeBtn?.visibility = View.INVISIBLE
        }
    }

    private fun changeClickabilty(views: List<View?>, editMode: Boolean) =
        views.forEach {
            it?.isEnabled = editMode
        }


    @SuppressLint("ClickableViewAccessibility")
    private fun setButtonResizers() {
        enterEditModeBtn?.setOnTouchListener(Utils.getResizerOnTouchListener(enterEditModeBtn))
        cancelEditModeBtn?.setOnTouchListener(Utils.getResizerOnTouchListener(cancelEditModeBtn))
        saveEditModeBtn?.setOnTouchListener(Utils.getResizerOnTouchListener(saveEditModeBtn))
        membersBtn?.setOnTouchListener(Utils.getResizerOnTouchListener(membersBtn))
        contactBtn?.setOnTouchListener(Utils.getResizerOnTouchListener(contactBtn))
    }

    private fun setClickListeners() {
        enterEditModeBtn?.setOnClickListener { presenter?.enterEditMode() }
        cancelEditModeBtn?.setOnClickListener {
            presenter?.discardChanges(
                org.description,
                org.location
            )
        }
        saveEditModeBtn?.setOnClickListener {
            presenter?.saveChanges(
                getTxt(descriptionEdtxt),
                getTxt(locationEdtxt)
            )
        }

    }

    private fun getTxt(editText: EditText?): String? {
        return editText?.editableText?.toString()?.trim()
    }
}