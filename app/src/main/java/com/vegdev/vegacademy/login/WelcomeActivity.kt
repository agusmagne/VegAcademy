package com.vegdev.vegacademy.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.utils.LayoutUtils
import com.vegdev.vegacademy.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    private val layoutUtils = LayoutUtils()
    private lateinit var firebaseAuth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser

        val text =
            "Hola, " + firebaseUser?.displayName?.split(" ", ignoreCase = true, limit = 2)?.first()
        hello_txt.text = text


        if (intent.getBooleanExtra("EXIT", false)) {
            onBackPressed()
        }

        start_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            layoutUtils.overrideEnterAndExitTransitions(this)
        }
    }

    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }
}
