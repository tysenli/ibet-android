package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.UserProfile.Banking.DepositMethod.AstropayInfo.Companion.carddate
import com.app.android.ibet.activity.UserProfile.Banking.DepositMethod.AstropayInfo.Companion.cardnum
import com.app.android.ibet.activity.UserProfile.Banking.DepositMethod.AstropayInfo.Companion.cvv
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_amount_input.*
import kotlinx.android.synthetic.main.activity_total.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class Astropay : Fragment() {
    //private var parentContext = context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_amount_input, container, false)
    }

    override fun onStart() {
        super.onStart()
        money_25.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(201, 199, 199))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = "25"
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_50.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(201, 199, 199))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = "50"
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_100.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(201, 199, 199))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = "100"
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_250.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(201, 199, 199))
            amount_display.text = "250"
            MyAccount.depo_amt = amount_display.text.toString()
        }

        deposit_amount2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                amount_display.text = deposit_amount2.text.toString()
                MyAccount.depo_amt = amount_display.text.toString()

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


        btn_wechat_dep.setOnClickListener {

            val client = OkHttpClient()

            val astroJson = JSONObject()
            val JSON = MediaType.get("application/json; charset=utf-8")
            //println("hhh" + cardnum)
            astroJson.put("card_num", cardnum)
            astroJson.put("card_code", cvv)
            astroJson.put("exp_date", carddate)
            astroJson.put("amount", amount_display.text.toString())
            val body = RequestBody.create(JSON, astroJson.toString())
            // println(token)
            val request = Request.Builder()
                .addHeader("Authorization", "Token " + Login.token)
                .url(BuildConfig.ASTROPAY)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            if (response.code() != 200) {
                MyAccount.info = "fail"
                val res = Intent(context, MyAccount::class.java)
                startActivity(res)
            } else {
                val statusData = response.body()!!.string()
                //println(JSONObject(statusData).getString("response_msg").substring(0,6))

                if (JSONObject(statusData).getString("response_msg").substring(0,6) == "1|1|1|") {
                    val user = JSONObject(MyAccount.userData).getString("username")
                    val depositJson = JSONObject()
                    depositJson.put("type", "add")
                    depositJson.put("username", user)
                    depositJson.put("balance", amount_display.text.toString())
                    val balance = Api().post(depositJson.toString(), BuildConfig.BALANCE)
                    MyAccount.info = "success"
                    val res = Intent(context, MyAccount::class.java)
                    //res.putExtra("amount",intent.getStringExtra("balance"))
                    startActivity(res)

                } else {
                    MyAccount.info = "fail"
                    val res = Intent(context, MyAccount::class.java)
                    startActivity(res)
                }
            }
            //println(response.body()!!.string())
        }
    }
}