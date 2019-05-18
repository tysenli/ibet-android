package com.example.proj2.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.proj2.ibet.R
import com.example.proj2.ibet.activity.MainActivity
import kotlinx.android.synthetic.main.activity_one_click_intro.*

class oneClickIntro:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_click_intro)
        one_click_intro_1.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        one_click_intro_2.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}