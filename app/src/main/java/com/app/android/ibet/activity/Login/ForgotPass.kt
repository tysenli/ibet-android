package com.app.android.ibet.activity.Login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.api.Api
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
            val info = Api().post(forgetCodeJson.toString(), BuildConfig.FORGET_CODE )
            //Log.e("status",info)
            if (info!!.substring(1,info!!.length - 1) == "Success") {
                val info2 = Api().post(forgetCodeJson.toString(), BuildConfig.FORGET_SEND_EMAIL)

                val res = Intent(this, NewPass::class.java)

                res.putExtra("mail",email_id2.text.toString())
                startActivity(res)
            } else {
                email_error1.text = "No such email exists."
                email_error1.setTextColor(Color.RED)
            }


        }

    }
}