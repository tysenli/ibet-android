package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.api.Api
import com.app.android.ibet.api.URLs
import kotlinx.android.synthetic.main.activity_phone_code.*
import kotlinx.android.synthetic.main.activity_veri_phone.*
import org.json.JSONObject

class VeriPhone : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_veri_phone)

        veri_phone.setOnClickListener {
            //post to generateactivationcode
            val generateCodeJson = JSONObject()
            generateCodeJson.put("username",intent.getStringExtra("user"))
            //http://10.0.2.2:8000/users/api/generateactivationcode/
           
            val info = Api().post(generateCodeJson.toString(), URLs.GENERATE_CODE )
            Api().myLog("generate phone code: $info")
            Log.e("info", info)

            val res = Intent(applicationContext, PhoneCode::class.java)
            res.putExtra("phone_num", "+" + country_code_picker.selectedCountryCode.toString() + "  " + phone.text.toString())
            res.putExtra("user",intent.getStringExtra("user"))
            //res.putExtra("code",code1.text.toString() + code2.text.toString() + code3.text.toString() + code4.text.toString())
            startActivity(res)

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