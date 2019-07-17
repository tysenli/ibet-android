package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_veri_email.*
import okhttp3.*





class VeriEmail : AppCompatActivity() {

    var client = OkHttpClient()
    //var request = OkHttpRequest(client)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // res.putExtra("user", intent.getStringExtra(emailAuthP2.USER))
       // res.putExtra("email",intent.getStringExtra(emailAuthP1.MAIL))
        setContentView(R.layout.activity_veri_email)

        //http://10.0.2.2:8000/users/api/sendemail/?case=signup&username=test&to_email_address=jiaqi@claymore.com...

        veri_email.setOnClickListener {
            var urlBuilder = HttpUrl.parse("http://10.0.2.2:8000/users/Api/sendemail/?case=signup")!!.newBuilder()
            println(email_verify.text.toString())
            urlBuilder.addQueryParameter("username", intent.getStringExtra("user"))
            urlBuilder.addQueryParameter("to_email_address",email_verify.text.toString())
            urlBuilder.addQueryParameter("email",email_verify.text.toString())
            val url = urlBuilder.build().toString()
            println(url)
            val request = Request.Builder()
                .url(url)
                .build()
            println(request)
            val response = OkHttpClient().newCall(request).execute()
            println(response.body()!!.string())
            startActivity(Intent(applicationContext, Verified::class.java))

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