package com.vegdev.vegacademy.view.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.login.LoginContract
import com.vegdev.vegacademy.presenter.login.CreateUserPresenter
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUserActivity : AppCompatActivity(), LoginContract.View.CreateUser {

    private val presenter = CreateUserPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        start_create_user_filled_btn.setOnClickListener {
            presenter.createUserIntent(
                name_txt.text.toString(),
                email_txt.text.toString(),
                password_txt.text.toString(),
                confpassword_txt.text.toString()
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
