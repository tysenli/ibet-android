package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_phone_code.*
import org.json.JSONObject

class PhoneCode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_code)
        phone_num.text = intent.getStringExtra("phone_num")
        code1.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(code1.text.toString().length == 1) {
                    code2.requestFocus()
                }

            }

        })
        code2.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(code2.text.toString().length == 1) {
                    code3.requestFocus()
                }

            }

        })
        code3.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(code3.text.toString().length == 1) {
                    code4.requestFocus()
                }

            }

        })
        code4.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val veriCodeJson = JSONObject()
                veriCodeJson.put("username",intent.getStringExtra("user"))
                veriCodeJson.put("code",code1.text.toString() + code2.text.toString() + code3.text.toString() + code4.text.toString())
                //http://10.0.2.2:8000/users/api/generateactivationcode/
                val info = Signup().post(veriCodeJson.toString(), BuildConfig.VERI_CODE )
                //println(info)
                val status = info.split(":")[1]
                println (status)
                if (status.substring(1,status.length - 2) == "Success") {
                    startActivity(Intent(applicationContext, Verified::class.java))
                } else {
                    code_error.text = "Sorry, that's not the code we're looking for. Please check and try again."
                    code_error.setTextColor(Color.RED)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })


    }
}