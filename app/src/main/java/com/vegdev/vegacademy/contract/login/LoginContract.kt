package com.vegdev.vegacademy.contract.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation

interface LoginContract {

    interface View {

        interface First {
            fun getLogo(): android.view.View
            fun getLogoIn(): android.view.View
            fun hideRlUnsigned()
            fun animateRlUnsigned(animation: Animation)
        }
        interface Start {
            fun onCreateUserClickBuildAnimationOptions(): Bundle
            fun onCreateUserClickStartAnimations(animation: Animation)
            fun onLoginClickBuildAnimationOptions(): Bundle
            fun changeButtonsAlpha()
        }
        interface CreateUser {
            fun showProgressbar()
            fun hideProgressbar()
        }
        interface Login {
            fun beginDelayedTransition()
        }
        interface Welcome {
            fun setText(text: String)
            fun onBackPressed()
        }

    }

    interface Actions {


        interface First {
            fun onInitAnimate()
        }
        interface Start {
            fun createFacebookLoginIntent()
            fun buildAnimationAndStartCreateUserActivity()
            fun buildAnimationAndStartLoginActivity()
            fun onActivityResult(requestCode: Int, resultCode: Int)
            fun onRestartAnimateColor()
        }
        interface CreateUser {
            fun createUserIntent(name: String, email: String, password: String, confPassword: String)
        }
        interface Login {
            fun signInIntent(email: String, password: String)
        }
        interface Welcome {
            fun setUserName()
            fun shouldExit(intent: Intent?)
            fun startMainActivity()
        }


    }
}