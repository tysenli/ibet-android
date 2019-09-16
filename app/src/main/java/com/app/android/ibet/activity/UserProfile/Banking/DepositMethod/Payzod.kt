package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.api.URLs
import kotlinx.android.synthetic.main.activity_amount_input.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class Payzod : Fragment() {
    //private var parentContext = context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_amount_input, container, false)
    }

    override fun onStart() {
        super.onStart()
        depo_method_show.text = "Payzod"
        deposit_amount2.hint = " Deposit 500 - 500,000                        Other"
        amt_input_err.visibility = View.GONE
        money_25.text = "500"
        money_50.text = "5000"
        money_100.text = "50000"
        money_250.text = "500000"
        money_25.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(201,199,199))
            money_50.setBackgroundColor(Color.rgb(239,239,239))
            money_100.setBackgroundColor(Color.rgb(239,239,239))
            money_250.setBackgroundColor(Color.rgb(239,239,239))
            amount_display.text =  money_25.text
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_50.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239,239,239))
            money_50.setBackgroundColor(Color.rgb(201,199,199))
            money_100.setBackgroundColor(Color.rgb(239,239,239))
            money_250.setBackgroundColor(Color.rgb(239,239,239))
            amount_display.text = money_50.text
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_100.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239,239,239))
            money_50.setBackgroundColor(Color.rgb(239,239,239))
            money_100.setBackgroundColor(Color.rgb(201,199,199))
            money_250.setBackgroundColor(Color.rgb(239,239,239))
            amount_display.text =  money_100.text
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_250.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239,239,239))
            money_50.setBackgroundColor(Color.rgb(239,239,239))
            money_100.setBackgroundColor(Color.rgb(239,239,239))
            money_250.setBackgroundColor(Color.rgb(201,199,199))
            amount_display.text = money_250.text.toString()
            MyAccount.depo_amt = amount_display.text.toString()
        }

        deposit_amount2.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                amount_display.text = deposit_amount2.text.toString()
                MyAccount.depo_amt = amount_display.text.toString()

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
        change_method.setOnClickListener {
            MyAccount.info = "deposit"
            val intent = Intent(activity, MyAccount::class.java)
            startActivity(intent)
            activity!!.overridePendingTransition(0, 0)
        }
        btn_wechat_dep.setOnClickListener {
            if (amount_display.text.toString() == "" || amount_display.text.toString().toFloat() < 500 || amount_display.text.toString().toFloat() > 500000) {
                amt_input_err.visibility = View.VISIBLE
                amt_input_err.text = "Please deposit between 500 - 500000"
            } else {
                amt_input_err.visibility = View.GONE
                val client = OkHttpClient()

                val payzodJson = JSONObject()
                val JSON = MediaType.get("application/json; charset=utf-8")
                payzodJson.put("amount", amount_display.text.toString())

                val body = RequestBody.create(JSON, payzodJson.toString())
                val request = Request.Builder()
                    .addHeader("Authorization", "Token " + Login.token)
                    .url(URLs.PAYZOD)
                    .post(body)
                    .build()
                val response = client.newCall(request).execute()
                if (response.code() != 200) {
                    MyAccount.info = "fail"
                    val res = Intent(context, MyAccount::class.java)
                    startActivity(res)
                } else {
                    val statusData = response.body()!!.string()
                    val res = Intent(activity, PayzodQR::class.java)
                    res.putExtra("QRcode", statusData)
                    startActivity(res)

                }
            }
        }
    }
}