package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login
import com.wajahatkarim3.easyvalidation.core.view_ktx.maxLength
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import kotlinx.android.synthetic.main.activity_email_auth_p2.*
import kotlinx.android.synthetic.main.activity_phone_code.*
import kotlinx.android.synthetic.main.activity_veri_phone.*
import org.json.JSONObject

class PhoneCode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_code)
        phone_num.text = intent.getStringExtra("phone_num")
        code4.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val veriCodeJson = JSONObject()
                veriCodeJson.put("username",intent.getStringExtra("user"))
                veriCodeJson.put("code",code1.text.toString() + code2.text.toString() + code3.text.toString() + code4.text.toString())
                //http://10.0.2.2:8000/users/api/generateactivationcode/
                val info = Signup().post(veriCodeJson.toString(), BuildConfig.VERI_CODE )
                println(info)
                startActivity(Intent(applicationContext, Verified::class.java))
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })


    }
}