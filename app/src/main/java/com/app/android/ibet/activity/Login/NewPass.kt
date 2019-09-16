package com.app.android.ibet.activity.Login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.api.Api
import com.app.android.ibet.api.URLs
import kotlinx.android.synthetic.main.activity_newpass.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class NewPass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_newpass)
        veri_code1.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(veri_code1.text.toString().length == 1) {
                    veri_code2.requestFocus()
                }

            }

        })
        veri_code2.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(veri_code2.text.toString().length == 1) {
                    veri_code3.requestFocus()
                }

            }

        })
        veri_code3.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(veri_code3.text.toString().length == 1) {
                    veri_code4.requestFocus()
                }

            }

        })

        confirm.setOnClickListener {
            val newCodeJson = JSONObject()
            newCodeJson.put("email",intent.getStringExtra("mail"))
            newCodeJson.put("code",veri_code1.text.toString() + veri_code2.text.toString() + veri_code3.text.toString() + veri_code4.text.toString())
            newCodeJson.put("password", new_password.text.toString())
            //http://10.0.2.2:8000/users/api/generateactivationcode/
            val info = Api().post(newCodeJson.toString(), URLs.VERI_PASS_CODE )
            //println(info)
            if (info!!.substring(1,info.length - 1) == "Success") {
                code_error2.text = "Success!"
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                code_error2.text = "Sorry, that's not the code we're looking for. Please check and try again."
                code_error2.setTextColor(Color.RED)
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