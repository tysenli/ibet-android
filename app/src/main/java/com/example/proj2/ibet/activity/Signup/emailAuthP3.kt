package com.example.proj2.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.proj2.ibet.R
import com.example.proj2.ibet.activity.MainActivity
import kotlinx.android.synthetic.main.activity_email_auth_p1.*
import kotlinx.android.synthetic.main.activity_email_auth_p3.*

class emailAuthP3: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_auth_p3)
        close3.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))

        }
    }
}