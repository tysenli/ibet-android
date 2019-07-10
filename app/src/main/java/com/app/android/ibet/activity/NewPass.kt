package com.app.android.ibet.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.Signup.emailAuthP1
import com.wajahatkarim3.easyvalidation.core.view_ktx.minLength
import kotlinx.android.synthetic.main.activity_email_auth_p1.*
import kotlinx.android.synthetic.main.activity_forgot_pass.*
import kotlinx.android.synthetic.main.activity_newpass.*
import kotlinx.android.synthetic.main.activity_phone_code.*
import org.json.JSONObject

class NewPass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_newpass)
        c1.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(c1.text.toString().length == 1) {
                    c2.requestFocus()
                }

            }

        })
        c2.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(c2.text.toString().length == 1) {
                    c3.requestFocus()
                }

            }

        })
        c3.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(c3.text.toString().length == 1) {
                    c4.requestFocus()
                }

            }

        })

        confirm.setOnClickListener {
            val newCodeJson = JSONObject()
            newCodeJson.put("email",intent.getStringExtra("mail"))
            newCodeJson.put("code",c1.text.toString() + c2.text.toString() + c3.text.toString() + c4.text.toString())
            newCodeJson.put("password", new_password.text.toString())
            //http://10.0.2.2:8000/users/api/generateactivationcode/
            val info = Signup().post(newCodeJson.toString(), BuildConfig.VERI_PASS_CODE )
            println(info)
            if (info.substring(1,info.length - 1) == "Success") {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                code_error2.text = "Sorry, that's not the code we're looking for. Please check and try again."
                code_error2.setTextColor(Color.RED)
            }

        }

    }
}