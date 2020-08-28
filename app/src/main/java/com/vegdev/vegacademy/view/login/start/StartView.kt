package com.vegdev.vegacademy.view.login.start

import android.os.Bundle
import android.view.animation.Animation

interface StartView {

    fun onCreateUserClickBuildAnimationOptions(): Bundle
    fun onCreateUserClickStartAnimations(animation: Animation)
    fun onLoginClickBuildAnimationOptions(): Bundle
    fun changeButtonsAlpha()

}