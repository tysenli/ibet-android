package com.app.android.ibet.activity.OnBoarding

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_welcome1.*

class IntroOne: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome1)

        welcome1.setOnClickListener {
            startActivity(Intent(applicationContext, IntroTwo::class.java))
        }
    }

}