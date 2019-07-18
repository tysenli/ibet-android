package com.app.android.ibet.activity.UserProfile.DepositMethod

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_wechat.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class QaiAli : AppCompatActivity() {
    var userData = Api().get(BuildConfig.USER)
    var orderId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_wechat)
        var pk =  JSONObject(userData).getString("pk")


        btn_wechat_dep.setOnClickListener {
            val client = OkHttpClient()
            val formBody = FormBody.Builder()
                .add("amount",deposit_amount2.text.toString())
                .add("user_id",pk)
                .add("currency","0")
                .add("language","zh-Hans")
                .add("method","ALIPAY_H5")
                .build()
            val request = Request.Builder()
                .url(BuildConfig.WECHAT)
                .post(formBody)
                .build()
            val response = client.newCall(request).execute()
            var wechatData = response.body()!!.string()
            orderId = JSONObject(wechatData).getJSONObject("depositTransaction").getString("orderId")
            var wechat_url = JSONObject(wechatData).getJSONObject("paymentPageSession").getString("paymentPageUrl")

            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(wechat_url)))


        }

    }

    override fun onResume() {
        super.onResume()
        println("HHAHHAHHAHAH")
        val user = JSONObject(userData).getString("username")
        if (orderId.isNotEmpty()) {
            val orderBody = FormBody.Builder()
                .add("order_id", orderId)
                .build()
            val request = Request.Builder()
                .url(BuildConfig.WECHAT_ORDER)
                .post(orderBody)
                .build()
            val response = OkHttpClient().newCall(request).execute()
            //println("hhh" + response2.body()!!.string())
            val statusData = response.body()!!.string()
            println(JSONObject(statusData).getString("status"))

            if (JSONObject(statusData).getString("status") == "SUCCESS") {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Success")
                builder.setMessage("success payment!!")
                val dialog = builder.create()
                dialog.show()
                val depositJson = JSONObject()
                depositJson.put("type", "add")
                depositJson.put("username", user)
                depositJson.put("balance", deposit_amount2.text.toString())
                val info = Api().post(depositJson.toString(), BuildConfig.BALANCE)
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Wrong")
                builder.setMessage("wrong payment!!")
                val dialog = builder.create()
                dialog.show()
            }
        }
    }
}