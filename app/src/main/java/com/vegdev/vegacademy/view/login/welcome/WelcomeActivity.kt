package com.vegdev.vegacademy.view.login.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.presenter.login.welcome.WelcomePresenter
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity(), WelcomeView {

    private val presenter = WelcomePresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        presenter.setUserName()
        presenter.shouldExit(intent)

        start_btn.setOnClickListener { presenter.startMainActivity() }
    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }

    override fun setText(text: String) {
        hello_txt.text = text
    }
}
