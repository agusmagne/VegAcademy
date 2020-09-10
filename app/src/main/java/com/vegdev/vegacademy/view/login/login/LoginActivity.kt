package com.vegdev.vegacademy.view.login.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.TransitionManager
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.presenter.login.login.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {

    private val presenter = LoginPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
            presenter.signInIntent(email_txt.text.toString().trim(), password_txt.text.toString())
        }
    }

    override fun beginDelayedTransition() {
        TransitionManager.beginDelayedTransition(login_constraint)
    }
}