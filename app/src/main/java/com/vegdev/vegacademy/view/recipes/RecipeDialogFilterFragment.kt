package com.vegdev.vegacademy.view.recipes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.model.data.models.Filter
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.main.IMainView
import kotlinx.android.synthetic.main.fragment_recipe_dialog_filter.*

class RecipeDialogFilterFragment : DialogFragment() {

    private val layoutUtils = LayoutUtils()
    private var iMainView: IMainView? = null
    private lateinit var filters: MutableList<Filter>
    private val FILTER_TASTE = 1
    private val FILTER_MEAL = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iMainView?.let { filters = it.getRecipeFilters() }
        return inflater.inflate(R.layout.fragment_recipe_dialog_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tasteFilter = filters.filter { it.type == FILTER_TASTE }
        val tasteFilterTitle: String?
        if (tasteFilter.isNotEmpty()) {
            tasteFilterTitle = tasteFilter[0].title
            (taste_group.children.find { (it as RadioButton).text == tasteFilterTitle } as RadioButton).isChecked =
                true
        }

        val mealFilter = filters.filter { it.type == FILTER_MEAL }
        val mealFilterTitle: String?
        if (mealFilter.isNotEmpty()) {
            mealFilterTitle = mealFilter[0].title
            (meal_group.children.find { (it as RadioButton).text == mealFilterTitle } as RadioButton).isChecked =
                true
        }

        cancel.setOnClickListener {
            dialog?.dismiss()
        }

        accept.setOnClickListener {
            val selectedTasteString: String =
                taste_group.findViewById<RadioButton>(taste_group.checkedRadioButtonId).text.toString()
            val selectedMealString: String =
                meal_group.findViewById<RadioButton>(meal_group.checkedRadioButtonId).text.toString()

            iMainView?.updateFilters(selectedTasteString, selectedMealString)

            dialog?.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()

        // set height and width
        val width = resources.displayMetrics.widthPixels - layoutUtils.toPx(32)
        val height = resources.displayMetrics.heightPixels - layoutUtils.toPx(380)
        val params = dialog?.window?.attributes
        params?.width = width; params?.height = height
        dialog?.window?.attributes = params

        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_add_recipe)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMainView) iMainView = context
    }

    override fun onDetach() {
        super.onDetach(); iMainView = null
    }
}