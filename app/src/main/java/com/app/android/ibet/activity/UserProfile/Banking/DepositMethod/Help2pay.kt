package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
<<<<<<< HEAD
import android.text.format.Time
=======
>>>>>>> c6b46c1bd3a9e3efaaafb4d3c7b5f379d12f875d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.userData
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_amount_input.*
import kotlinx.android.synthetic.main.frag_banking_depo.*
import kotlinx.android.synthetic.main.frag_help2pay.*
import okhttp3.*
import org.json.JSONObject

import java.util.*

class Help2pay : Fragment() {
    //private var parentContext = context
    var time = 0
    var orderId = "ibet " + Calendar.getInstance().time
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_help2pay, container, false)
    }

    override fun onStart() {
        time++
        super.onStart()
        var cur = ""
        var bank = ""
        //var time = Calendar.getInstance().time


        //println(orderId)

        val currency = arrayOf("THB", "VND")

        val bankthb = arrayOf("Karsikorn QaiBankWith (K-QaiBankWith)","Bangkok QaiBankWith","Siam Commercial QaiBankWith","Krung Thai QaiBankWith",
            "QaiBankWith of Ayudhya (Krungsri)","Government Savings QaiBankWith","TMB QaiBankWith Public Company Limited","CIMB Thai","Kiatnakin QaiBankWith")

        val bankvnd = arrayOf("Techcom QaiBankWith","Sacom QaiBankWith","Vietcom QaiBankWith","Asia Commercial QaiBankWith","DongA QaiBankWith","Vietin QaiBankWith",
            "QaiBankWith for Investment and Development of Vietnam","Eximbank Vietnam")

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
            if (help2pay_amt.text.toString() != "" && help2pay_amt.text.toString().toFloat() > 0) {
                val client = OkHttpClient()
                val formBody = FormBody.Builder()
                    .add("amount", help2pay_amt.text.toString())
                    .add("user_id", JSONObject(userData).getString("pk"))
                    .add("currency", cur)
                    .add("bank", bank)
                    .add("language", "en-Us")
                    .add("order_id", orderId)
                    .build()

                val request = Request.Builder()
                    .addHeader("Authorization", "Token " + Login.token)
                    .url(BuildConfig.Help2pay)
                    .post(formBody)
                    .build()
                val response = client.newCall(request).execute()
                //println(response)
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
    override fun onResume() {
        super.onResume()
        val user = JSONObject(userData).getString("username")
        if (time > 1) {

            val client = OkHttpClient()

            val help2payJson = JSONObject()
            val JSON = MediaType.get("application/json; charset=utf-8")

            help2payJson.put("order_id", orderId)

            val body = RequestBody.create(JSON, help2payJson.toString())
            val request = Request.Builder()
                .addHeader("Authorization", "Token " + Login.token)
                .url(BuildConfig.Help2pay_CONFIRM)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            if (response.code() != 200) {
                MyAccount.info = "fail"
                val res = Intent(context, MyAccount::class.java)
                startActivity(res)
            } else {

                val statusData = response.body()!!.string()

                if (statusData == "0") {

                    val depositJson = JSONObject()
                    depositJson.put("type", "add")
                    depositJson.put("username", user)
                    depositJson.put("balance", help2pay_amt.text.toString())
                    val info = Api().post(depositJson.toString(), BuildConfig.BALANCE)
                    val res = Intent(context, Success::class.java)
                    res.putExtra("amount", help2pay_amt.text.toString())
                    startActivity(res)

                } else {
                    MyAccount.info = "fail"
                    val res = Intent(context, MyAccount::class.java)
                    startActivity(res)
                }
            }
        }
    }
}