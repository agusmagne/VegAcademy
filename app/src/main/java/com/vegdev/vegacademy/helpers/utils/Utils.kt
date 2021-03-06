package com.vegdev.vegacademy.helpers.utils

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.firebase.Timestamp
import com.vegdev.vegacademy.R
import java.util.concurrent.TimeUnit

class Utils {

    companion object {

        fun makeVisibleInvisible(visibleViews: List<View?>?, insvisibleViews: List<View?>?) {
            visibleViews?.let { list ->
                list.forEach { view ->
                    view?.let { it.visibility = View.VISIBLE }
                }
            }
            insvisibleViews?.let { list ->
                list.forEach { view ->
                    view?.let { it.visibility = View.INVISIBLE }
                }
            }
        }

        fun overrideEnterAndExitTransitions(activity: Activity) {
            activity.overridePendingTransition(R.anim.welcome_fade_in, R.anim.welcome_fade_out)
        }

        fun startActivity(context: Context, activity: Any) {
            val intent = Intent(context, activity as Class<*>)
            context.startActivity(intent)
        }

        fun getDateDifference(date1: Timestamp, date2: Timestamp, timeUnit: TimeUnit): Long {
            val diffInMillis = date2.seconds - date1.seconds
            return timeUnit.convert(diffInMillis, TimeUnit.SECONDS)
        }

        fun getTranslation(name: String, context: Context): String {
            return context.getString(
                context.resources.getIdentifier(
                    name,
                    "string",
                    context.packageName
                )
            )
        }

        fun getFirstWord(string: String?): String? =
            string?.split(" ", ignoreCase = true, limit = 2)?.first()

        @SuppressLint("ClickableViewAccessibility")
        fun getResizerOnTouchListener(view: View?): View.OnTouchListener {
            return View.OnTouchListener { p0, p1 ->
                p1?.let {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            val reducer =
                                AnimatorInflater.loadAnimator(view?.context, R.animator.resize)
                            reducer.setTarget(view)
                            reducer.start()
                        }

                        MotionEvent.ACTION_UP -> {
                            val reducer =
                                AnimatorInflater.loadAnimator(view?.context, R.animator.regain)
                            reducer.setTarget(view)
                            reducer.start()
                        }
                        else -> {
                        }
                    }
                }
                false
            }
        }

        fun toDp(px: Int): Int = (px / Resources.getSystem().displayMetrics.density).toInt()
        fun toPx(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()

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

        fun getAverageColor(bitmap: Bitmap): List<Int> {
            var red = 0
            var green = 0
            var blue = 0
            var pixelCount = 0

            for (x in 0 until bitmap.width) {
                for (y in 0 until bitmap.height) {
                    val pixel = bitmap.getPixel(x, y)
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

}