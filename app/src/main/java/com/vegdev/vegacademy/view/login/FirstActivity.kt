package com.vegdev.vegacademy.view.login

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.contract.login.LoginContract
import com.vegdev.vegacademy.presenter.login.FirstPresenter
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity(), LoginContract.View.First {

    private lateinit var firebaseAuth: FirebaseAuth

    private var presenter = FirstPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        presenter.onInitAnimate()
    }

    override fun getLogo(): View = logo

    override fun getLogoIn(): View = logo_in

    override fun hideRlUnsigned() {
        rl_unsigned.visibility = View.GONE
    }

    override fun animateRlUnsigned(animation: Animation) {
        rl_unsigned.startAnimation(animation)
    }
}