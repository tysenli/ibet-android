package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_paypal.*
import okhttp3.*
import org.json.JSONObject

class Paypal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_paypal)
        btn_paypal_dep.setOnClickListener {
            val client = OkHttpClient()
            val formBody = FormBody.Builder()
                .add("amount",deposit_amount.text.toString())
                .add("currency","USD")
                .add("user","Jennie")
                .build()
            val request = Request.Builder()
                //.addHeader("Authorization", "token " + Login.token)
                .url(BuildConfig.PAYPAL)
                .post(formBody)
                .build()
            val response = client.newCall(request).execute()

            var jsonData = response.body()!!.string()
            var jobject = JSONObject(jsonData)
            var link = jobject.getJSONArray("links").getJSONObject(1).getString("href")
            startActivity(Intent(Intent.ACTION_VIEW,Uri.parse(link)))
            println(link)
            val orderId = link.substring(link.length - 20,link.length)
            println(orderId)
            val orderBody = FormBody.Builder()
                .add("order_id",orderId)
                .add("user","Jennie")
                .build()

            val request2 = Request.Builder()
                //.addHeader("Authorization", "token " + Login.token)
                .url(BuildConfig.PAYPAL_ORDER)
                .post(orderBody)
                .build()
            val response2 = client.newCall(request).execute()
            println("this is:" + response2.body()!!.string())



        }
    }
}