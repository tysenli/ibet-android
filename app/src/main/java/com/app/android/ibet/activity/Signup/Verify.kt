package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_verify.*

class Verify: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_verify)
        via_email.setOnClickListener {
            val res = Intent(applicationContext, VeriEmail::class.java)
            res.putExtra("user", intent.getStringExtra(emailAuthP2.USER))
            res.putExtra("email",intent.getStringExtra(emailAuthP1.MAIL))
            startActivity(res)


        }
        via_phone.setOnClickListener {
            startActivity(Intent(applicationContext, VeriPhone::class.java))

        }
    }
}