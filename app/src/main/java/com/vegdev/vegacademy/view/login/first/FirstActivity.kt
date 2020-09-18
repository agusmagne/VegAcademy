package com.vegdev.vegacademy.view.login.first

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.vegdev.vegacademy.R
import com.vegdev.vegacademy.presenter.login.first.FirstPresenter
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity(), FirstView {

    private lateinit var firebaseAuth: FirebaseAuth

    private var presenter = FirstPresenter(this, this, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        firebaseAuth = FirebaseAuth.getInstance()

    }

    override fun onStart() {
        super.onStart()

        presenter.onInitAnimate()

//            // if user is logged in go to WelcomeActivity
//            // if user's not logged in go StartActivity
//            if (firebaseAuth.currentUser != null) {
//
//                val animation = animateLogo()
//                rl_unsigned.startAnimation(animation)
//            } else {
//                val act: Activity = this
//                val cont: Context = this
//
//                val handler = Handler()
//                handler.postDelayed({
//                    val p1 = android.util.Pair.create(logo as View, "logo")
//                    val options =
//                        ActivityOptions.makeSceneTransitionAnimation(act, p1)
//                    val intent = Intent(cont, StartActivity::class.java)
//                    cont.startActivity(intent, options.toBundle())
//                }, 1000)
//
//
//            }
    }

//    private fun animateLogo(): Animation {
//        val cont: Context = this
//        val act: Activity = this
//        val animation = AnimationUtils.loadAnimation(this,
//            R.anim.welcome_fade_out
//        )
//        val p1 = android.util.Pair.create(logo_in as View, "logo")
//        animation.startOffset = 1000
//        animation.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationRepeat(animation: Animation?) {}
//            override fun onAnimationStart(animation: Animation?) {}
//            override fun onAnimationEnd(animation: Animation?) {
//                rl_unsigned.visibility = View.GONE
//                val intent = Intent(cont, WelcomeActivity::class.java)
//                val options = ActivityOptions.makeSceneTransitionAnimation(act, p1)
//                cont.startActivity(intent, options.toBundle())
//            }
//        })
//        return animation


    override fun getLogo(): View = logo

    override fun getLogoIn(): View = logo_in

    override fun hideRlUnsigned() {
        rl_unsigned.visibility = View.GONE
    }

    override fun animateRlUnsigned(animation: Animation) {
        rl_unsigned.startAnimation(animation)
    }
}