package com.app.android.ibet.activity.Login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_forgot_pass.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class ForgotPass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgot_pass)
        send_email.setOnClickListener {
            val forgetCodeJson = JSONObject()
            forgetCodeJson.put("email",email_id2.text.toString())
            val client = OkHttpClient()

            val JSON = MediaType.get("application/json; charset=utf-8")
            val body = RequestBody.create(JSON, forgetCodeJson.toString())

            val request = Request.Builder()
                    // .addHeader("Authorization", "Bearer $token")
                    .url(BuildConfig.FORGET_CODE)
                    .post(body)
                    .build()

            val response = client.newCall(request).execute()
            //http://10.0.2.2:8000/users/api/generateactivationcode/
            //val info = Api().post(forgetCodeJson.toString(), BuildConfig.FORGET_CODE )
            println(response.code())
            if (response.code() == 200) {
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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            android.R.id.home -> {
                // startActivity(Intent(this, MyAccount::class.java))
                onBackPressed()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }
}