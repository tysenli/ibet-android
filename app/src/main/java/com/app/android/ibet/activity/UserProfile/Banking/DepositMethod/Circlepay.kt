package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.renderscript.Element
import androidx.fragment.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.UserProfile.MyAccount
import kotlinx.android.synthetic.main.activity_amount_input.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.math.BigInteger
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class Circlepay : Fragment() {
    //private var parentContext = context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_amount_input, container, false)
    }

    override fun onStart() {
        super.onStart()
        depo_method_show.text = "Circlepay"
        deposit_amount2.hint = " Deposit 20 - 50000                        Other"
        amt_input_err.visibility = View.GONE
        money_25.text = "20"
        money_50.text = "200"
        money_100.text = "2000"
        money_250.text = "20000"
        money_25.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(201, 199, 199))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = money_25.text
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_50.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(201, 199, 199))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = money_50.text
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_100.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(201, 199, 199))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = money_100.text
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_250.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(201, 199, 199))
            amount_display.text = money_250.text
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

        change_method.setOnClickListener {
            MyAccount.info = "deposit"
            val intent = Intent(activity, MyAccount::class.java)
            startActivity(intent)
            activity!!.overridePendingTransition(0, 0)
        }

        btn_wechat_dep.setOnClickListener {
            if (amount_display.text.toString() == "" || amount_display.text.toString().toFloat() < 20 || amount_display.text.toString().toFloat() > 50000) {
                amt_input_err.visibility = View.VISIBLE
                amt_input_err.text = "Please deposit between 20 - 50000"
            } else {
                amt_input_err.visibility = View.GONE
                val key = "Kiy4O3IAvPpHxXJ9ht1mBfZs"
                val transId =
                    JSONObject(MyAccount.userData).getString("username") + "CirclePay" + Calendar.getInstance().time
                val message = "jennyto@ibet.com$transId" + amount_display.text.toString()
                var hasher = Mac.getInstance("HmacSHA256")
                hasher.init(SecretKeySpec(key.toByteArray(), "HmacSHA256"))
                val hash = hasher.doFinal(message.toByteArray())
                //println(BigInteger(1,hash).toString(16))
                val ciclepay_url =
                    "https://gateway.circlepay.ph/payment/" + "297802061195" + "/?partner_tran_id=$transId" + "&amount=" +
                            amount_display.text.toString() + "&token=" + BigInteger(1, hash).toString(16)
                /*
                val client = OkHttpClient()

                val circleJson = JSONObject()
                val JSON = MediaType.get("application/json; charset=utf-8")
                circleJson.put("amount", amount_display.text.toString())
                circleJson.put("trans_id", transId)

                val body = RequestBody.create(JSON, circleJson.toString())
                val request = Request.Builder()
                    .addHeader("Authorization", "Token " + Login.token)
                    .url(BuildConfig.Circlepay)
                    .post(body)
                    .build()
                val response = client.newCall(request).execute()
                Log.e("response",response.body()!!.string())  */
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ciclepay_url)))
            }
        }


    }
}