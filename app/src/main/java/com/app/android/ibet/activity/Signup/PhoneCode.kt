package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_phone_code.*
import kotlinx.android.synthetic.main.activity_veri_phone.*

class PhoneCode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_code)
        phone_num.text = intent.getStringExtra("phone_num")

    }
}