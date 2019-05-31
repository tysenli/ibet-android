package com.app.android.ibet.activity.OnBoarding

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import kotlinx.android.synthetic.main.activity_welcome4.*

class IntroFour :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome4)
        wel_4_1.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        wel_4_2.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}