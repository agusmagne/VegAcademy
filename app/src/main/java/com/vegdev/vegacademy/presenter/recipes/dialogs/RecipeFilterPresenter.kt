package com.vegdev.vegacademy.presenter.recipes.dialogs

import android.app.Dialog
import android.content.Context
import com.vegdev.vegacademy.model.data.models.Filter
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.main.MainView
import com.vegdev.vegacademy.view.recipes.dialogs.filter.RecipeFilterDialogView

class RecipeFilterPresenter(
    private val context: Context,
    private val view: RecipeFilterDialogView,
    private val iMainView: MainView
) {

    private val FILTER_TASTE = 1
    private val FILTER_MEAL = 2
    private val FILTER_BOTH = "Ambos"
    private val FILTER_WARNING = "No has cambiado ningún filtro"

    private val layoutUtils = LayoutUtils()
    private var filters: MutableList<Filter> = iMainView.getRecipeFilters()
    private var tasteFilter = Filter("", 0)
    private var mealFilter = Filter("", 0)

    fun checkButtonsOnInit() {

        val tasteFilterList = filters.filter { it.type == FILTER_TASTE }
        if (tasteFilterList.isNotEmpty()) {
            tasteFilter = tasteFilterList[0]
            view.checkTasteButton(tasteFilter.title)
        } else {
            tasteFilter.title = FILTER_BOTH
        }

        val mealFilterList = filters.filter { it.type == FILTER_MEAL }
        if (mealFilterList.isNotEmpty()) {
            mealFilter = mealFilterList[0]
            view.checkMealButton(mealFilter.title)
        } else {
            mealFilter.title = FILTER_BOTH
        }
    }

    fun updateFilters(taste: String, meal: String) {
        if (taste == tasteFilter.title && meal == mealFilter.title) {
            iMainView.makeToast(FILTER_WARNING)
        } else {
            iMainView.updateFilters(taste, meal)
            view.dismiss()
        }
    }


    fun setLayoutParams(dialog: Dialog?) {
        dialog?.let {
            val width =
                dialog.context.resources.displayMetrics.widthPixels - layoutUtils.toPx(32)
            val height =
                dialog.context.resources.displayMetrics.heightPixels - layoutUtils.toPx(380)

            val params = dialog.window?.attributes
            params?.width = width
            params?.height = height

            view.setLayoutParams(params)
        }
    }


}