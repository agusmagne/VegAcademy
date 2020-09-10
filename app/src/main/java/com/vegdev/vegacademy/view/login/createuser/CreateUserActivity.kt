package com.vegdev.vegacademy.view.login.createuser

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.presenter.login.createuser.CreateUserPresenter
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUserActivity : AppCompatActivity(), CreateUserView {

    private val presenter = CreateUserPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        back_btn.setOnClickListener { onBackPressed() }

        create_btn.setOnClickListener {
            presenter.createUserIntent(
                name_txt.text.toString(),
                email_txt.text.toString(),
                password_txt.text.toString(),
                confpassword_txt.text.toString()
            )
        }
    }

    override fun changePasswordImageColor() {
        confpassword_field.setImageResource(R.drawable.redroundborder40dp)
    }

    override fun startProgressBarAnimation(animation: Animation) {
        progress_bar.startAnimation(animation)
        progress_bar_background.startAnimation(animation)
        progress_bar.visibility = View.VISIBLE
        progress_bar_background.visibility = View.VISIBLE
    }

}
