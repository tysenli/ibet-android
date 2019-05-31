package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_one_click.*

class oneClick : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_click)
        info.text = "Username: " + intent.getStringExtra("username") + "\n" + "Password: " + intent.getStringExtra("password")
        btn_ok.setOnClickListener {
            startActivity(Intent(applicationContext, oneClickIntro::class.java))
        }
    }
}