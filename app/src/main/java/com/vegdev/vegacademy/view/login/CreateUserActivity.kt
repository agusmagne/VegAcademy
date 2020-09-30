package com.vegdev.vegacademy.view.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.login.LoginContract
import com.vegdev.vegacademy.presenter.login.CreateUserPresenter
import com.vegdev.vegacademy.utils.Utils
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUserActivity : AppCompatActivity(), LoginContract.View.CreateUser {

    private val presenter = CreateUserPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        logo.setOnTouchListener(Utils.getResizerOnTouchListener(logo))
        logo.setOnClickListener {  }

        create_btn.setOnTouchListener(Utils.getResizerOnTouchListener(create_btn))
        create_btn.setOnClickListener {
            presenter.createUserIntent(
                name_txt.text.toString().trim(),
                email_txt.text.toString().trim(),
                password_txt.text.toString().trim(),
                confpassword_txt.text.toString().trim()
            )
        }
    }

    override fun showProgressbar() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        progress_bar.visibility = View.INVISIBLE
    }

}
