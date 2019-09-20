package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.api.Api
import com.app.android.ibet.api.URLs
import kotlinx.android.synthetic.main.activity_thirdparty.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class QaiBankOpenPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_thirdparty)
        val myweb = findViewById<WebView>(R.id.webview)
        val setting = myweb.settings
        setting.javaScriptEnabled = true
        myweb.loadUrl(intent.getStringExtra("qai_bankurl"))
        myweb.webViewClient = WebViewClient()
        depo_method_show.background = resources.getDrawable(R.drawable.payment_bank)
        payment_method.text = "Bank"
        order_number.text = intent.getStringExtra("qai_bankorderId")
        time.text = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        deposit_check.setOnClickListener {
            val user = JSONObject(MyAccount.userData).getString("username")

            val orderBody = FormBody.Builder()
                .add("trans_id", intent.getStringExtra("qai_bankorderId"))
                .build()
            val request = Request.Builder()
                .url(URLs.QAICASH_CONFIRM)
                .post(orderBody)
                .build()
            val response = OkHttpClient().newCall(request).execute()
            println(response)
            if (response.code() != 200) {
                MyAccount.info = "fail"
                val res = Intent(this, MyAccount::class.java)
                startActivity(res)
            } else {
                val statusData = response.body()!!.string()
                //println(JSONObject(statusData).getString("status"))

                if (JSONObject(statusData).getInt("status") == 0) {

                    val depositJson = JSONObject()
                    depositJson.put("type", "add")
                    depositJson.put("username", user)
                    depositJson.put("balance", intent.getStringExtra("qai_bankbalance"))
                    val balance = Api().post(depositJson.toString(), URLs.BALANCE)
                    MyAccount.info = "success"
                    val res = Intent(this, MyAccount::class.java)
                    //res.putExtra("amount",intent.getStringExtra("balance"))
                    startActivity(res)

                } else {
                    MyAccount.info = "fail"
                    val res = Intent(this, MyAccount::class.java)
                    startActivity(res)
                }
            }
        }
        deposit_cancel.setOnClickListener {
            MyAccount.info = "deposit"
            val intent = Intent(this, MyAccount::class.java)
            startActivity(intent)
            this!!.overridePendingTransition(0, 0)
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