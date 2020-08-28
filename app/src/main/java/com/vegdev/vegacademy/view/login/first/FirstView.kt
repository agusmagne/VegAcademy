package com.vegdev.vegacademy.view.login.first

import android.view.View
import android.view.animation.Animation

interface FirstView {

    fun getLogo(): View
    fun getLogoIn(): View
    fun hideRlUnsigned()
    fun animateRlUnsigned(animation: Animation)

}