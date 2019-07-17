package com.app.android.ibet.activity.UserProfile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_wechat.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.lang.Exception

class Wechat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_wechat)

        /*
    val request = Request.Builder()
        .header("Authorization", "Token " + Login.token)
        //  .url(Filename/....)
        .url(BuildConfig.USER)
        .build()
    val response = OkHttpClient().newCall(request).execute()
    */

        var jsonData = Api().get(BuildConfig.USER)

        var pk =  JSONObject(jsonData).getString("pk")

        btn_wechat_dep.setOnClickListener {
            val client = OkHttpClient()
            val formBody = FormBody.Builder()
                .add("amount",deposit_amount2.text.toString())
                .add("user_id",pk)
                .add("currency","0")
                .add("language","zh-Hans")
                .add("method","WECHAT_PAY")
                .build()
            val request = Request.Builder()
                .url(BuildConfig.WECHAT)
                .post(formBody)
                .build()
            val response = client.newCall(request).execute()
            var jsonData = response.body()!!.string()

            var wechat_url = JSONObject(jsonData).getJSONObject("paymentPageSession").getString("paymentPageUrl")

            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(wechat_url)))


            val orderBody = FormBody.Builder()
                    .add("order_id", JSONObject(jsonData).getJSONObject("depositTransaction").getString("orderId"))
                    .build()
            val request2 = Request.Builder()
                    .url(BuildConfig.WECHAT_ORDER)
                    .post(orderBody)
                    .build()
            val response2 = client.newCall(request2).execute()


        }

    }
}