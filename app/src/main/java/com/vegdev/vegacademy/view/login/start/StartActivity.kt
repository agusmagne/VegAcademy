package com.vegdev.vegacademy.view.login.start

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.presenter.login.start.StartPresenter
import kotlinx.android.synthetic.main.activity_create_user.logo
import kotlinx.android.synthetic.main.activity_start.*
import android.util.Pair as UtilPair

class StartActivity : AppCompatActivity(), StartView {

    private val presenter = StartPresenter(this, this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        fb_login_btn.setOnClickListener { presenter.createFacebookLoginIntent() }
        start_create_user_btn.setOnClickListener { presenter.buildAnimationAndStartCreateUserActivity() }
        login_txt.setOnClickListener { presenter.buildAnimationAndStartLoginActivity() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode)
    }

    override fun onRestart() {
        super.onRestart()
        presenter.onRestartAnimateColor()
    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }

    override fun onCreateUserClickBuildAnimationOptions(): Bundle {
        val p1 = UtilPair.create(create_btn as View, "image_slides_down")
        val p2 = UtilPair.create(start_create_user_filled_txt as View, "text_slides_down")
        val p3 = UtilPair.create(logo as View, "logo")
        return ActivityOptions.makeSceneTransitionAnimation(this, p1, p2, p3).toBundle()
    }

    override fun onCreateUserClickStartAnimations(animation: Animation) {
        start_create_user_btn.startAnimation(animation)
        start_create_user_txt.startAnimation(animation)
    }

    override fun onLoginClickBuildAnimationOptions(): Bundle {
        val p1 = android.util.Pair.create(login_txt as View, "login_text")
        val p2 = android.util.Pair.create(login_btn as View, "login_image")
        val p3 = android.util.Pair.create(logo as View, "logo")
        return ActivityOptions.makeSceneTransitionAnimation(this, p1, p2, p3).toBundle()

    }

    override fun changeButtonsAlpha() {
        start_create_user_btn.alpha = 1f
        start_create_user_txt.alpha = 1f
    }

}
