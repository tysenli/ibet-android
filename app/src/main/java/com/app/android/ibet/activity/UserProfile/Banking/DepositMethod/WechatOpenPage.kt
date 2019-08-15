package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.userData
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_amount_input.*
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.activity_total.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class WechatOpenPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test)
        val myweb = findViewById<WebView>(R.id.webview)
        val setting = myweb.settings
        setting.javaScriptEnabled = true
        myweb.loadUrl(intent.getStringExtra("wechaturl"))
        myweb.webViewClient = WebViewClient()

        deposit_check.setOnClickListener {
            val user = JSONObject(userData).getString("username")

            val orderBody = FormBody.Builder()
                .add("order_id", intent.getStringExtra("orderId"))
                .build()
            val request = Request.Builder()
                .url(BuildConfig.WECHAT_ORDER)
                .post(orderBody)
                .build()
            val response = OkHttpClient().newCall(request).execute()
            println(response)
            if (response.code() != 200) {
                info = "fail"
                val res = Intent(this, MyAccount::class.java)
                startActivity(res)
            } else {
                val statusData = response.body()!!.string()
                println(JSONObject(statusData).getString("status"))

                if (JSONObject(statusData).getString("status") == "SUCCESS") {

                    val depositJson = JSONObject()
                    depositJson.put("type", "add")
                    depositJson.put("username", user)
                    depositJson.put("balance", intent.getStringExtra("balance"))
                    val balance = Api().post(depositJson.toString(), BuildConfig.BALANCE)
                    info = "success"
                    val res = Intent(this, MyAccount::class.java)
                    //res.putExtra("amount",intent.getStringExtra("balance"))
                    startActivity(res)

                } else {
                    info = "fail"
                    val res = Intent(this, MyAccount::class.java)
                    startActivity(res)
                }
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