package com.example.proj2.ibet.activity.OnBoarding

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.proj2.ibet.R
import com.example.proj2.ibet.activity.MainActivity
import kotlinx.android.synthetic.main.activity_welcome3.*
import kotlinx.android.synthetic.main.activity_welcome4.*

class Welcome4 :AppCompatActivity(){

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