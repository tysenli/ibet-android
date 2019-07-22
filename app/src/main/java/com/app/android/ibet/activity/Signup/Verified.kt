package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import kotlinx.android.synthetic.main.activity_verified.*

class Verified : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_verified)
        veri_login.setOnClickListener {
            startActivity(Intent(applicationContext, Login::class.java))

        }
    }
}