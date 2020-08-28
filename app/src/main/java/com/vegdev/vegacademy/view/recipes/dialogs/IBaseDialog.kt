package com.vegdev.vegacademy.view.recipes.dialogs

import android.view.WindowManager

interface IBaseDialog {

    fun dismiss()
    fun setLayoutParams(params: WindowManager.LayoutParams?)
}