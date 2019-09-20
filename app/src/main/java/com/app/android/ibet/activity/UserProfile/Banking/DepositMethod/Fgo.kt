package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.userData
import com.app.android.ibet.api.Api
import com.app.android.ibet.api.URLs
import kotlinx.android.synthetic.main.activity_amount_input.*
import kotlinx.android.synthetic.main.frag_fgo.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class Fgo : Fragment() {
    //private var parentContext = context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_fgo, container, false)
    }

    override fun onStart() {
        super.onStart()
        //var userData = Api().get(BuildConfig.USER)


        fgo_deposit.setOnClickListener {

            val client = OkHttpClient()
            val formBody = FormBody.Builder()
                .add("pin", fgo_pin.text.toString())
                .add("user", JSONObject(userData).getString("username"))
                .add("serial", fgo_serial.text.toString())
                .build()

            val request = Request.Builder()
                .addHeader("Authorization", "Token " + Login.token)
                .url(URLs.Fgate)
                .post(formBody)
                .build()
            val response = client.newCall(request).execute()

            if (response.code() != 200) {
                MyAccount.info = "fail"
                val res = Intent(context, MyAccount::class.java)
                startActivity(res)
            } else {
                val statusData = response.body()!!.string()

                if (JSONObject(statusData).getString("error_code") == "00" && JSONObject(statusData).getString("status") == "1") {
                    val user = JSONObject(userData).getString("username")
                    val depositJson = JSONObject()
                    depositJson.put("type", "add")
                    depositJson.put("username", user)
                    depositJson.put("balance", JSONObject(statusData).getString("amount"))
                    val balance = Api().post(depositJson.toString(), URLs.BALANCE)
                    MyAccount.info = "success"
                    val res = Intent(context, MyAccount::class.java)
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