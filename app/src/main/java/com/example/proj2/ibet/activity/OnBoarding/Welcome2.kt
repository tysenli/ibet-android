package com.example.proj2.ibet.activity.OnBoarding

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.proj2.ibet.R
import kotlinx.android.synthetic.main.activity_welcome1.*
import kotlinx.android.synthetic.main.activity_welcome2.*

class Welcome2 :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome2)
        welcome2.setOnClickListener {
            startActivity(Intent(applicationContext, Welcome3::class.java))
        }
    }
}