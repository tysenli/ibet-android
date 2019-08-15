package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.Time
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.userData
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.frag_fgo.*
import kotlinx.android.synthetic.main.frag_help2pay.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

import java.util.*

class Help2pay : Fragment() {
    //private var parentContext = context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_help2pay, container, false)
    }

    override fun onStart() {
        super.onStart()
        var cur = ""
        var bank = ""
        //var time = Calendar.getInstance().time

        var orderId = "ibet " + Calendar.getInstance().time
        //println(orderId)

        val currency = arrayOf("THB", "VND")

        val bankthb = arrayOf("Karsikorn Bank (K-Bank)","Bangkok Bank","Siam Commercial Bank","Krung Thai Bank",
            "Bank of Ayudhya (Krungsri)","Government Savings Bank","TMB Bank Public Company Limited","CIMB Thai","Kiatnakin Bank")

        val bankvnd = arrayOf("Techcom Bank","Sacom Bank","Vietcom Bank","Asia Commercial Bank","DongA Bank","Vietin Bank",
            "Bank for Investment and Development of Vietnam","Eximbank Vietnam")

        val thbvalue = arrayOf("KKR","BBL","SCB","KTB","BOA","GSB","TMB","CIMBT","KNK")
        val vndvalue = arrayOf("TCB","SACOM","VCB","ACB","DAB","VTB","BIDV","EXIM")


        if (help2pay_currency != null) {
            val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,currency)

            help2pay_currency.adapter = arrayAdapter

            help2pay_currency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(context, getString(R.string.selected_item) + " " + currency[position], Toast.LENGTH_SHORT).show()
                    if (position == 0) {
                        cur = "2"
                        help2pay_bank.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,bankthb)
                    } else {
                        cur = "8"
                        help2pay_bank.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,bankvnd)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }

        }

        help2pay_bank.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (cur) {
                    "2" -> bank = thbvalue[position]
                    "8" -> bank = vndvalue[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        btn_help2pay.setOnClickListener {

            val client = OkHttpClient()
            val formBody = FormBody.Builder()
                .add("amount", help2pay_amt.text.toString())
                .add("user_id", JSONObject(userData).getString("pk"))
                .add("currency", cur)
                .add("bank",bank)
                .add("language","en-Us")
                .add("order_id",orderId)
                .build()

            val request = Request.Builder()
                .addHeader("Authorization", "Token " + Login.token)
                .url(BuildConfig.Help2pay)
                .post(formBody)
                .build()
            val response = client.newCall(request).execute()
            println(response)
            if (response.code() != 200) {
                MyAccount.info = "fail"
                val res = Intent(context, MyAccount::class.java)
                startActivity(res)
            } else {
                val statusData = response.body()!!.string()
                val res = Intent(activity, Help2payOpenPage::class.java)
                res.putExtra("help2pay", statusData)
                startActivity(res)
                //println(statusData)

            }
        }


    }
}