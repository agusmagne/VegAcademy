package com.vegdev.vegacademy.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.core.graphics.get
import com.vegdev.vegacademy.R
import kotlin.math.roundToInt

class LayoutUtils {

    fun overrideEnterAndExitTransitions(activity: Activity) {
        activity.overridePendingTransition(R.anim.welcome_fade_in, R.anim.welcome_fade_out)
    }

    fun startActivity(context: Context, activity: Any) {
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

    fun createToast(context: Context, text: String) {
        val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        toast.show()
    }

    fun animateIn(view: View, context: Context) {
        val animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
        animation.duration = 400
        animation.startOffset = 100
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {
                view.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                view.clearAnimation()
            }

        })
        view.startAnimation(animation)
    }

    fun animateOut(view: View, context: Context, id: Int) {
        val animation = AnimationUtils.loadAnimation(context, id)
        animation.duration = 400
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                view.visibility = View.GONE
            }
        })
        view.startAnimation(animation)
    }

    fun animateViews(
        context: Context,
        animationId: Int,
        viewArray: List<View>
    ) {
        val animation = AnimationUtils.loadAnimation(context, animationId)
        for (view in viewArray) {
            view.startAnimation(animation)
        }
    }

    fun getAverageColor(bitmap: Bitmap) : List<Int>{
        var red = 0
        var green = 0
        var blue = 0
        var pixelCount = 0

        for (x in 0 until bitmap.height) {
            for (y in 0 until bitmap.width) {
                val pixel = bitmap[x, y]
                pixelCount++
                red += Color.red(pixel)
                green += Color.green(pixel)
                blue += Color.blue(pixel)
            }
        }
        val avgRed = red / pixelCount
        val avgGreen = green / pixelCount
        val avgBlue = blue / pixelCount
        return listOf(avgRed, avgGreen, avgBlue)
    }

}