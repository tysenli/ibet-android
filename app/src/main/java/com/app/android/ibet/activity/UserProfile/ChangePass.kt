package com.app.android.ibet.activity.UserProfile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import kotlinx.android.synthetic.main.activity_change_pass.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class ChangePass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)

        change_pass.setOnClickListener {

            var correctNewPass = false
            var notsame = false
            if (cur_pass.text.toString().equals(new_pass.text.toString())) {
                new_pass_error.text = "new password cannot be same as the past."
                new_pass_error.setTextColor(Color.RED)
            } else {
                new_pass_error.text = ""
                notsame = true
            }
            if (!new_pass.text.toString().equals(confirm_pass.text.toString())) {
                new_pass_error.text = "two new password is not same."
                new_pass_error.setTextColor(Color.RED)
            } else {
                new_pass_error.text = ""
                correctNewPass = true
            }

            if (notsame && correctNewPass) {

                var changeJson = JSONObject()
                changeJson.put("current_password", cur_pass.text.toString())
                changeJson.put("new_password", new_pass.text.toString())

                val client = OkHttpClient()

                val JSON = MediaType.get("application/json; charset=utf-8")
                val body = RequestBody.create(JSON, changeJson.toString())
                val request = Request.Builder()
                    .addHeader("Authorization", "token " + Login.token)
                    .url(BuildConfig.CHANGE_PASS)
                    .post(body)
                    .build()

                val response = client.newCall(request).execute()

                val status = response.body()!!.string().split(":")[1]
                println(status.substring(1, status.length - 2))
                if (status.substring(1, status.length - 2) == "Failed") {
                    cur_pass_error.text = "current password not correct."
                    cur_pass_error.setTextColor(Color.RED)
                    println("check cur")

                } else {
                    cur_pass_error.text = ""
                    println("check cur2")
                    startActivity(Intent(this, MainActivity::class.java))
                }

            }

            //println(response.body()!!.string())
        }

    }
}