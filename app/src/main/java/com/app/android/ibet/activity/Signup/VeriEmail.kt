package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_veri_email.*

class VeriEmail : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_veri_email)
        veri_email.setOnClickListener {
            startActivity(Intent(applicationContext, Verified::class.java))

        }
    }
}