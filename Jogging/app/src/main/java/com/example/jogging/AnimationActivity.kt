package com.example.jogging

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView




class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        val appTitle = findViewById<TextView>(R.id.textAnim)
        val ty = ObjectAnimator.ofFloat(appTitle, View.TRANSLATION_Y, appTitle.translationY, appTitle.translationY + 1000f)
        ty.duration = 2450
        ty.interpolator = LinearInterpolator()
        ty.start()
        val splashScreenTimeOut = 2500
        val homeIntent = Intent(this, MainActivity::class.java)

        Handler().postDelayed(
            {
                startActivity(homeIntent)
                finish()
            }
            , splashScreenTimeOut.toLong())
    }
}