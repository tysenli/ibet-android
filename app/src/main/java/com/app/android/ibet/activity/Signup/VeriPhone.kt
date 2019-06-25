package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_veri_phone.*

class VeriPhone : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_veri_phone)

        veri_phone.setOnClickListener {
            val res = Intent(applicationContext, PhoneCode::class.java)
            res.putExtra("phone_num", "+" + country_code_picker.selectedCountryCode.toString() + "  " + phone.text.toString())
            startActivity(res)

        }

    }
}