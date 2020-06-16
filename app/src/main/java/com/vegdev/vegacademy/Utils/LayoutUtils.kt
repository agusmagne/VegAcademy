package com.vegdev.vegacademy.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.widget.Toast
import com.vegdev.vegacademy.R
import kotlin.math.roundToInt

class LayoutUtils {

    fun overrideEnterAndExitTransitions(activity: Activity){
        activity.overridePendingTransition(R.anim.welcome_fade_in, R.anim.welcome_fade_out)
    }

    fun startActivity(context: Context, activity: Any){
        val intent = Intent(context, activity as Class<*>)
        context.startActivity(intent)
    }

    fun pxToDpYaxis(px: Int, context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return (px / (displayMetrics.ydpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    fun dpToPxYaxis(dp: Int, context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return (dp / (displayMetrics.ydpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    fun createToast(context: Context, text: String){
        val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        toast.show()
    }

}