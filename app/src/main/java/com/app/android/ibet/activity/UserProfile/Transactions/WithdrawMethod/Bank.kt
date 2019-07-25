package com.app.android.ibet.activity.UserProfile.Transactions.WithdrawMethod

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
import com.app.android.ibet.activity.UserProfile.Transactions.DepositMethod.Success
import com.app.android.ibet.activity.UserProfile.Transactions.Withdraw
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_amount_input.*
import kotlinx.android.synthetic.main.activity_amount_input.amount_display
import kotlinx.android.synthetic.main.activity_amount_input.money_100
import kotlinx.android.synthetic.main.activity_amount_input.money_25
import kotlinx.android.synthetic.main.activity_amount_input.money_250
import kotlinx.android.synthetic.main.activity_amount_input.money_50
import kotlinx.android.synthetic.main.activity_amount_input_withdraw.*
import kotlinx.android.synthetic.main.dialog.view.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class Bank : AppCompatActivity() {
    var userData = Api().get(BuildConfig.USER)
    var withdraworderId = ""
    val user = JSONObject(userData).getString("username")
    override fun onCreate(savedInstanceState: Bundle?) {
        val balance = JSONObject(userData).getString("main_wallet")
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_input_withdraw)
        // var pk = JSONObject(userData).getString("pk")
        money_25.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(201, 199, 199))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = "25"
        }
        money_50.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(201, 199, 199))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = "50"
        }
        money_100.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(201, 199, 199))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = "100"
        }
        money_250.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(201, 199, 199))
            amount_display.text = "250"
        }

        withdraw_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                amount_display.text = withdraw_amount.text.toString()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                money_25.setBackgroundColor(Color.rgb(239, 239, 239))
                money_50.setBackgroundColor(Color.rgb(239, 239, 239))
                money_100.setBackgroundColor(Color.rgb(239, 239, 239))
                money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            }

        })

        btn_bank_withdraw.setOnClickListener {
            println(balance < amount_display.text.toString())
            if (balance.toFloat() < amount_display.text.toString().toFloat() ) {
                withdraw_err.text = "Your balance is not enough!"
                withdraw_err.setTextColor(Color.RED)
            } else {
                withdraw_err.text = "Withdraw Amount"
                withdraw_err.setTextColor(Color.BLACK)
                val client = OkHttpClient()
                val formBody = FormBody.Builder()
                    .add("amount", amount_display.text.toString())
                    .add("user_id", user)
                    .add("currency", "CNY")
                    .add("method", "LOCAL_BANK_TRANSFER")
                    .add("language", "zh-Hans")
                    .build()
                val request = Request.Builder()
                    .url(BuildConfig.WITHDRAW)
                    .post(formBody)
                    .build()
                val response = client.newCall(request).execute()
                var withdrawData = response.body()!!.string()
                println(withdrawData)
                withdraworderId = JSONObject(withdrawData).getJSONObject("payoutTransaction").getString("orderId")
                var withdraw_url =
                    JSONObject(withdrawData).getJSONObject("paymentPageSession").getString("paymentPageUrl")
                val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
                val builder = AlertDialog.Builder(this)
                builder.setView(dialogView)
                val dialog = builder.show()
                dialogView.text.text = "Confirm Withdraw"
                dialogView.diposit_display.text = amount_display.text.toString() + " bank"
                dialogView.confirm.setOnClickListener {
                    dialog.dismiss()
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(withdraw_url)))
                }
                dialogView.cancel.setOnClickListener {
                    dialog.dismiss()
                }
            }


        }
        change_withdraw_method.setOnClickListener {
            startActivity(Intent(this, Withdraw::class.java))

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        title = "Withdraw"
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

        if (withdraworderId.isNotEmpty()) {
            val orderBody = FormBody.Builder()
                .add("order_id", withdraworderId)
                .build()
            val request = Request.Builder()
                .url(BuildConfig.WITHDRAW_ORDER)
                .post(orderBody)
                .build()
            val response = OkHttpClient().newCall(request).execute()
            //println("hhh" + response2.body()!!.string())
            val statusData = response.body()!!.string()
            //println(JSONObject(statusData).getString("status"))

            if (JSONObject(statusData).getString("status") == "HELD") {
                /*
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Success")
                builder.setMessage("success payment!!")
                val dialog = builder.create()
                dialog.show() */
                val depositJson = JSONObject()
                depositJson.put("type", "withdraw")
                depositJson.put("username", user)
                depositJson.put("balance", amount_display.text.toString())
                val info = Api().post(depositJson.toString(), BuildConfig.BALANCE)
                val res = Intent(this, SuccessWithdraw::class.java)
                res.putExtra("amount", amount_display.text.toString())
                startActivity(res)

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