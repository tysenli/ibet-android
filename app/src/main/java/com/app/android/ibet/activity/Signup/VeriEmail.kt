package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R

import com.app.android.ibet.api.Api

import com.app.android.ibet.api.URLs

import kotlinx.android.synthetic.main.activity_veri_email.*
import okhttp3.*





class VeriEmail : AppCompatActivity() {

    var client = OkHttpClient()
    //var request = OkHttpRequest(client)
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)
       // res.putExtra("user", intent.getStringExtra(emailAuthP2.USER))
       // res.putExtra("email",intent.getStringExtra(emailAuthP1.MAIL))
        setContentView(R.layout.activity_veri_email)

        //http://10.0.2.2:8000/users/api/sendemail/?case=signup&username=test&to_email_address=jiaqi@claymore.com...

        veri_email.setOnClickListener {

            var urlBuilder = HttpUrl.parse(URLs.VERI_SIGNUP)!!.newBuilder()
    
            urlBuilder.addQueryParameter("username", intent.getStringExtra("user"))
            urlBuilder.addQueryParameter("to_email_address",email_verify.text.toString())
            urlBuilder.addQueryParameter("email",email_verify.text.toString())
            val url = urlBuilder.build().toString()

            val request = Request.Builder()
                .url(url)
                .build()

            val response = OkHttpClient().newCall(request).execute()
            Api().myLog("emailVerify:" + response.body()!!.string())
            startActivity(Intent(applicationContext, Verified::class.java))

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
    /*
    fun GET(url: String, callback: Callback): Call {
        val request = Request.Builder()
            .url(url)
            .build()

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    } */


}