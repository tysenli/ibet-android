package com.app.android.ibet.activity.UserProfile.Transactions.DepositMethod

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.Transactions.Deposit
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_amount_input.*
import kotlinx.android.synthetic.main.dialog.view.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class QaiAli : AppCompatActivity() {
    var userData = Api().get(BuildConfig.USER)
    var orderId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_input)
        var pk =  JSONObject(userData).getString("pk")
        money_25.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(201,199,199))
            money_50.setBackgroundColor(Color.rgb(239,239,239))
            money_100.setBackgroundColor(Color.rgb(239,239,239))
            money_250.setBackgroundColor(Color.rgb(239,239,239))
            amount_display.text =  "25"
        }
        money_50.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239,239,239))
            money_50.setBackgroundColor(Color.rgb(201,199,199))
            money_100.setBackgroundColor(Color.rgb(239,239,239))
            money_250.setBackgroundColor(Color.rgb(239,239,239))
            amount_display.text = "50"
        }
        money_100.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239,239,239))
            money_50.setBackgroundColor(Color.rgb(239,239,239))
            money_100.setBackgroundColor(Color.rgb(201,199,199))
            money_250.setBackgroundColor(Color.rgb(239,239,239))
            amount_display.text =  "100"
        }
        money_250.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239,239,239))
            money_50.setBackgroundColor(Color.rgb(239,239,239))
            money_100.setBackgroundColor(Color.rgb(239,239,239))
            money_250.setBackgroundColor(Color.rgb(201,199,199))
            amount_display.text = "250"
        }

        deposit_amount2.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                amount_display.text = deposit_amount2.text.toString()

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                money_25.setBackgroundColor(Color.rgb(239,239,239))
                money_50.setBackgroundColor(Color.rgb(239,239,239))
                money_100.setBackgroundColor(Color.rgb(239,239,239))
                money_250.setBackgroundColor(Color.rgb(239,239,239))
            }

        })

        btn_wechat_dep.setOnClickListener {
            val client = OkHttpClient()
            val formBody = FormBody.Builder()
                .add("amount",amount_display.text.toString())
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
            var aliData = response.body()!!.string()
            orderId = JSONObject(aliData).getJSONObject("depositTransaction").getString("orderId")
            var ali_url = JSONObject(aliData).getJSONObject("paymentPageSession").getString("paymentPageUrl")
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog,null)
            val builder = AlertDialog.Builder(this)
            builder.setView(dialogView)
            val dialog = builder.show()
            dialogView.text.text = "Confirm Deposit"
            dialogView.diposit_display.text = amount_display.text.toString() + " AliPay"
            dialogView.confirm.setOnClickListener {
                dialog.dismiss()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ali_url)))

            }
            dialogView.cancel.setOnClickListener {
                dialog.dismiss()
            }

        }
        change_method.setOnClickListener {
            startActivity(Intent(this, Deposit::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        title = "Deposit"
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (!MainActivity.isLogin) {
            menu!!.findItem(R.id.logged).isVisible = false
            menu.findItem(R.id.login).isVisible = true
        } else {
            menu!!.findItem(R.id.logged).isVisible = true
            menu.findItem(R.id.login).isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
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
            R.id.deposit -> {
                startActivity(Intent(this, Signup::class.java))
                return true
            }
            R.id.login -> {
                startActivity(Intent(this, Login::class.java))
                return true
            }
            R.id.logged -> {
                startActivity(Intent(this, MyAccount::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }


    }


    override fun onResume() {
        super.onResume()
        //println("HHAHHAHHAHAH")
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
                depositJson.put("balance", amount_display.text.toString())
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