package com.vegdev.vegacademy.ui.recipes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.vegdev.vegacademy.IRecipeManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.fragment_recipe_dialog_filter.*

class RecipeDialogFilterFragment : DialogFragment() {

    private val layoutUtils = LayoutUtils()
    private var iRecipeManager: IRecipeManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_dialog_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancel.setOnClickListener {
            dialog?.dismiss()
        }

        accept.setOnClickListener {
            var selectedTasteString: String? =
                taste_group.findViewById<RadioButton>(taste_group.checkedRadioButtonId).text.toString()
            var selectedMealString: String? =
                meal_group.findViewById<RadioButton>(meal_group.checkedRadioButtonId).text.toString()

            if (selectedTasteString == "Ambos") selectedTasteString = null
            if (selectedMealString == "Ambos") selectedMealString = null

            iRecipeManager?.updateFilters(null, selectedTasteString, selectedMealString)
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
        if (context is IRecipeManager) iRecipeManager = context
    }

    override fun onDetach() {
        super.onDetach(); iRecipeManager = null
    }
}