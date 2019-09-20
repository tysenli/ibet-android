package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.api.Api
import com.app.android.ibet.api.URLs
import kotlinx.android.synthetic.main.activity_amount_input.*
import kotlinx.android.synthetic.main.frag_help2pay.*
import kotlinx.android.synthetic.main.frag_scratch.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class ScratchCard : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_scratch, container, false)
    }

    override fun onStart() {
        super.onStart()
        var ope = ""

        val operator = arrayOf("Viettel", "Vinaphone","Mobifone")
        val opeValue = arrayOf("vtt","vnp","vms")

        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,operator)
        scratch_operator.adapter = arrayAdapter
        scratch_operator.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                ope = opeValue[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        btn_scratch.setOnClickListener {
            val client = OkHttpClient()

            val scratchJson = JSONObject()
            val JSON = MediaType.get("application/json; charset=utf-8")
            //println("hhh" + cardnum)
            scratchJson.put("serial", scratch_serial.text.toString())
            scratchJson.put("pin", scratch_pin.text.toString())
            scratchJson.put("operator", ope)
            scratchJson.put("amount", scratch_amt.text.toString())
            val body = RequestBody.create(JSON, scratchJson.toString())
            val request = Request.Builder()
                .addHeader("Authorization", "Token " + Login.token)
                .url(URLs.Scratch)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            if (response.code() != 200) {
                MyAccount.info = "fail"
                val res = Intent(context, MyAccount::class.java)
                startActivity(res)
            } else {
                val statusData = response.body()!!.string()
                Api().myLog("scratchCard: $statusData")
                if (JSONObject(statusData).getString("status") == "1") {
                    val user = JSONObject(MyAccount.userData).getString("username")
                    val depositJson = JSONObject()
                    depositJson.put("type", "add")
                    depositJson.put("username", user)
                    depositJson.put("balance", scratch_amt.text.toString())
                    val balance = Api().post(depositJson.toString(), URLs.BALANCE)
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
        }
    }
}