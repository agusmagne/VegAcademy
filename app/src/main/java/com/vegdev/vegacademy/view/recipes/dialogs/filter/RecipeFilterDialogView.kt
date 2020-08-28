package com.vegdev.vegacademy.view.recipes.dialogs.filter

import com.vegdev.vegacademy.view.recipes.dialogs.IBaseDialog

interface RecipeFilterDialogView : IBaseDialog {

    fun checkTasteButton(title: String)
    fun checkMealButton(title: String)

}