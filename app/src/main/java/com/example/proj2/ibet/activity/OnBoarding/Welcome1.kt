package com.example.proj2.ibet.activity.OnBoarding

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.proj2.ibet.R
import com.example.proj2.ibet.activity.MainActivity
import kotlinx.android.synthetic.main.activity_welcome1.*

class Welcome1: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome1)

        welcome1.setOnClickListener {
            startActivity(Intent(applicationContext, Welcome2::class.java))
        }
    }

}