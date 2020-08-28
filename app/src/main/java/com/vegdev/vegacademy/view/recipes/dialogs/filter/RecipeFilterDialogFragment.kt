package com.vegdev.vegacademy.view.recipes.dialogs.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Filter
import com.vegdev.vegacademy.presenter.recipes.dialogs.RecipeFilterPresenter
import com.vegdev.vegacademy.view.main.MainView
import kotlinx.android.synthetic.main.fragment_recipe_dialog_filter.*

class RecipeFilterDialogFragment : DialogFragment(), RecipeFilterDialogView {

    private var mainView: MainView? = null
    private lateinit var filters: MutableList<Filter>

    private var presenter: RecipeFilterPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_dialog_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter?.checkButtonsOnInit()

        cancel.setOnClickListener {
            dialog?.dismiss()
        }

        accept.setOnClickListener {
            val selectedTasteString: String =
                taste_group.findViewById<RadioButton>(taste_group.checkedRadioButtonId).text.toString()
            val selectedMealString: String =
                meal_group.findViewById<RadioButton>(meal_group.checkedRadioButtonId).text.toString()

            presenter?.updateFilters(selectedTasteString, selectedMealString)
//            mainView?.updateFilters(selectedTasteString, selectedMealString)

        }
    }

    override fun onResume() {
        super.onResume()
        presenter?.setLayoutParams(dialog)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainView) presenter =
            RecipeFilterPresenter(context, this, context)
    }

    override fun onDetach() {
        super.onDetach(); mainView = null
    }

    override fun checkTasteButton(title: String) {
        (taste_group.children.find { (it as RadioButton).text == title } as RadioButton).isChecked =
            true
    }

    override fun checkMealButton(title: String) {
        (meal_group.children.find { (it as RadioButton).text == title } as RadioButton).isChecked =
            true
    }

    override fun setLayoutParams(params: WindowManager.LayoutParams?) {
        dialog?.window?.attributes = params
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_add_recipe)
    }
}