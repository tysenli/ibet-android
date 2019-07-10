package com.app.android.ibet.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.Signup.emailAuthP1
import com.app.android.ibet.activity.Signup.emailAuthP2
import kotlinx.android.synthetic.main.activity_forgot_pass.*
import org.json.JSONObject

class ForgotPass : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgot_pass)
        send_email.setOnClickListener {
            val forgetCodeJson = JSONObject()
            forgetCodeJson.put("email",email_id2.text.toString())
            //http://10.0.2.2:8000/users/api/generateactivationcode/
            val info = Signup().post(forgetCodeJson.toString(), BuildConfig.FORGET_CODE )
            println(info)
            if (info.substring(1,info.length - 1) == "Success") {
                val info2 = Signup().post(forgetCodeJson.toString(), BuildConfig.FORGET_SEND_EMAIL)

                val res = Intent(this, NewPass::class.java)

                res.putExtra("mail",email_id2.text.toString())
                startActivity(res)
            } else {
                textView25.text = "No email found."
                textView25.setTextColor(Color.RED)
            }


        }

    }
}