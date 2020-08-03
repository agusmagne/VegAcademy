package com.vegdev.vegacademy.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vegdev.vegacademy.MainActivity
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.utils.LayoutUtils
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    private val layoutUtils = LayoutUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if(intent.getBooleanExtra("EXIT", false)) {
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
