package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
<<<<<<< HEAD:app/src/main/java/com/app/android/ibet/activity/UserProfile/Banking/DepositMethod/BankDep.kt
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
=======
>>>>>>> c6b46c1bd3a9e3efaaafb4d3c7b5f379d12f875d:app/src/main/java/com/app/android/ibet/activity/UserProfile/Banking/DepositMethod/AsiaAli.kt
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_amount_input.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class AsiaAli : Fragment() {
    //private var parentContext = context
    var userData = Api().get(BuildConfig.USER)
    var orderId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_amount_input, container, false)
    }

    override fun onStart() {
        super.onStart()
        depo_method_show.text = "AliPay"
        deposit_amount2.hint = " Deposit 300 - 1,500                        Other"
        amt_input_err.visibility = View.GONE
        money_25.text = "300"
        money_50.text = "700"
        money_100.text = "1100"
        money_250.text = "1500"
        var pk = JSONObject(userData).getString("pk")
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
            if (amount_display.text.toString() == "" || amount_display.text.toString().toFloat() < 300 || amount_display.text.toString().toFloat() > 1500) {
                amt_input_err.visibility = View.VISIBLE
                amt_input_err.text = "Please deposit between 300 - 1500"
            } else {
                amt_input_err.visibility = View.GONE
                val client = OkHttpClient()
                val formBody = FormBody.Builder()
                    .add("amount", amount_display.text.toString())
                    .add("userid", pk)
                    .add("currency", "0")
                    .add("PayWay", "42")
                    .add("method", "41") //ali
                    .build()

                val request = Request.Builder()
                    .url(BuildConfig.ASIAPAY)
                    .post(formBody)
                    .build()
                val response = client.newCall(request).execute()
                if (response.code() != 200) {
                    MyAccount.info = "fail"
                    val res = Intent(context, MyAccount::class.java)
                    startActivity(res)
                } else {
                    var aliData = response.body()!!.string()
                    var aliurl = JSONObject(aliData).getString("qr")
                    orderId = JSONObject(aliData).getString("oid")
                    val res = Intent(activity, AsiaAliOpenPage::class.java)
                    res.putExtra("asia_aliurl", aliurl)
                    res.putExtra("asia_aliorderId", orderId)
                    res.putExtra("asia_alibalance", amount_display.text.toString())
                    startActivity(res)

                }
            }
            //println(response.code())
            //var quickData = response.body()!!.string()
            //println(quickData)
            /*
            orderId = JSONObject(quickData).getString("order_id")
            var url = JSONObject(quickData).getString("url")
            var quickpay_url = "$url?cid=BRANDCQNGHUA3&oid=$orderId"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(quickpay_url))) */
        }
    }
}

