package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_phone_code.*
import kotlinx.android.synthetic.main.activity_veri_phone.*
import org.json.JSONObject

class VeriPhone : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_veri_phone)

        veri_phone.setOnClickListener {
            //post to generateactivationcode
            val generateCodeJson = JSONObject()
            generateCodeJson.put("username",intent.getStringExtra("user"))
            //http://10.0.2.2:8000/users/api/generateactivationcode/
            val info = Api().post(generateCodeJson.toString(),BuildConfig.GENERATE_CODE )
            println(info)
            val res = Intent(applicationContext, PhoneCode::class.java)
            res.putExtra("phone_num", "+" + country_code_picker.selectedCountryCode.toString() + "  " + phone.text.toString())
            res.putExtra("user",intent.getStringExtra("user"))
            //res.putExtra("code",code1.text.toString() + code2.text.toString() + code3.text.toString() + code4.text.toString())
            startActivity(res)

        }

    }
}