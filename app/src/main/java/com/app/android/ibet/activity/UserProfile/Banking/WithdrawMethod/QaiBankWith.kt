package com.app.android.ibet.activity.UserProfile.Banking.WithdrawMethod

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.with_amt
import com.app.android.ibet.api.Api
import com.app.android.ibet.api.URLs
import kotlinx.android.synthetic.main.activity_amount_input.amount_display
import kotlinx.android.synthetic.main.activity_amount_input_withdraw.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class QaiBankWith: Fragment() {
    //private var parentContext = context
    var userData = Api().get(URLs.USER)
    var withdraworderId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_amount_input_withdraw, container, false)
    }

    override fun onStart() {
        super.onStart()
        var user = JSONObject(userData).getString("username")
        val balance = JSONObject(userData).getString("main_wallet")
        cancel_withdraw_method.setOnClickListener {
            MyAccount.info = "withdraw"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        btn_bank_withdraw.setOnClickListener {
            //println(balance < amount_display.text.toString())
            if (balance.toFloat() < amount_display.text.toString().toFloat() ) {
                amt_err.text = "Amount exceeds your available funds."
                amt_err.setTextColor(Color.RED)
            } else {
                with_amt = amount_display.text.toString()
                val client = OkHttpClient()
                val formBody = FormBody.Builder()
                    .add("amount", amount_display.text.toString())
                    .add("user_id", user)
                    .add("currency", "CNY")
                    .add("method", "LOCAL_BANK_TRANSFER")
                    .add("language", "zh-Hans")
                    .build()
                val request = Request.Builder()
                    .url(URLs.WITHDRAW)
                    .post(formBody)
                    .build()
                val response = client.newCall(request).execute()
                var withdrawData = response.body()!!.string()
                //println(withdrawData)
                withdraworderId = JSONObject(withdrawData).getJSONObject("payoutTransaction").getString("orderId")
                var withdraw_url = JSONObject(withdrawData).getJSONObject("paymentPageSession").getString("paymentPageUrl")


                //startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(withdraw_url)))
                val res = Intent(activity, QaiBankWithOpenPage::class.java)
                res.putExtra("bankurl", withdraw_url)
                res.putExtra("bankorderId",withdraworderId)
                res.putExtra("bankbnc",amount_display.text.toString())
                startActivity(res)

            }
        }
    }
}
    /*: AppCompatActivity() {
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
                dialogView.diposit_display.text = amount_display.text.toString() + " payment_bank"
                dialogView.confirm.setOnClickListener {
                    dialog.dismiss()
                    //startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(withdraw_url)))
                    val res = Intent(this, QaiBankWithOpenPage::class.java)
                    res.putExtra("bankurl", withdraw_url)
                    startActivity(res)
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
} */