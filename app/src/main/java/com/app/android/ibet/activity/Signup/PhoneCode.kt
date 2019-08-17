package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_phone_code.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class PhoneCode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_code)
        phone_num.text = intent.getStringExtra("phone_num")
        phone_code1.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(phone_code1.text.toString().length == 1) {
                    phone_code2.requestFocus()
                }

            }

        })
        phone_code2.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(phone_code2.text.toString().length == 1) {
                    phone_code3.requestFocus()
                }

            }

        })
        phone_code3.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(phone_code3.text.toString().length == 1) {
                    phone_code4.requestFocus()
                }

            }

        })
        phone_code4.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val veriCodeJson = JSONObject()
                veriCodeJson.put("username",intent.getStringExtra("user"))
                veriCodeJson.put("code",phone_code1.text.toString() + phone_code2.text.toString() + phone_code3.text.toString() + phone_code4.text.toString())
                //http://10.0.2.2:8000/users/api/generateactivationcode/
                //val info = Api().post(veriCodeJson.toString(), BuildConfig.VERI_CODE )
                //println(info)

                val client = OkHttpClient()

                val JSON = MediaType.get("application/json; charset=utf-8")
                val body = RequestBody.create(JSON, veriCodeJson.toString())

                val request = Request.Builder()
                    // .addHeader("Authorization", "Bearer $token")
                    .url(BuildConfig.VERI_CODE)
                    .post(body)
                    .build()

                val response = client.newCall(request).execute()
                println(response.code())
                if (response.code() == 200) {
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