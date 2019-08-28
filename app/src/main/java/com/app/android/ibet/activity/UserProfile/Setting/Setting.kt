package com.app.android.ibet.activity.UserProfile.Setting

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import kotlinx.android.synthetic.main.frag_responsible_game.*
import kotlinx.android.synthetic.main.frag_setting.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class Setting : Fragment() {
    //private var parentContext = context
    //var userData = Api().get(BuildConfig.USER)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_setting, container, false)
    }

    override fun onStart() {
        super.onStart()
        val request = Request.Builder()
            .url(BuildConfig.MARKETING + "?userId=" + JSONObject(MyAccount.userData).getString("pk"))
            .build()
        val response = OkHttpClient().newCall(request).execute()
        val marketData = response.body()!!.string()


        val request2 = Request.Builder()
            .url(BuildConfig.PRIVACY + "?userId=" + JSONObject(MyAccount.userData).getString("pk"))
            .build()
        val response2 = OkHttpClient().newCall(request2).execute()
        val privacyData = response2.body()!!.string()

        marketing_info.visibility = View.VISIBLE
        privacy_info.visibility = View.GONE
        marketing.background = resources.getDrawable(R.color.btn_d)
        privacy.background = resources.getDrawable(R.color.btn_l)
        com_phone.isChecked = JSONObject(marketData).getString("phone").toBoolean()
        com_sms.isChecked = JSONObject(marketData).getString("sms").toBoolean()
        com_email.isChecked = JSONObject(marketData).getString("email").toBoolean()
        com_postal.isChecked = JSONObject(marketData).getString("postal").toBoolean()
        com_social.isChecked = JSONObject(marketData).getString("socialMedia").toBoolean()

        bonuses.isChecked = JSONObject(privacyData).getString("bonus").toBoolean()
        vip.isChecked = JSONObject(privacyData).getString("vip").toBoolean()

        if (com_phone.isChecked or com_sms.isChecked or com_email.isChecked or com_postal.isChecked or com_social.isChecked) {
            communication.isChecked = true
        } else {
            communication.isChecked = false
            com_phone.isEnabled = false
            com_sms.isEnabled = false
            com_email.isEnabled = false
            com_postal.isEnabled = false
            com_social.isEnabled = false
        }

        marketing.setOnClickListener {
            marketing_info.visibility = View.VISIBLE
            privacy_info.visibility = View.GONE
            marketing.background = resources.getDrawable(R.color.btn_d)
            privacy.background = resources.getDrawable(R.color.btn_l)
        }
        privacy.setOnClickListener {
            marketing_info.visibility = View.GONE
            privacy_info.visibility = View.VISIBLE
            marketing.background = resources.getDrawable(R.color.btn_l)
            privacy.background = resources.getDrawable(R.color.btn_d)
        }
        communication.setOnCheckedChangeListener {  _,isChecked ->
            if (isChecked) {
                com_phone.isEnabled = true
                com_sms.isEnabled = true
                com_email.isEnabled = true
                com_postal.isEnabled = true
                com_social.isEnabled = true
            } else {
                com_phone.isEnabled = false
                com_sms.isEnabled = false
                com_email.isEnabled = false
                com_postal.isEnabled = false
                com_social.isEnabled = false
            }
        }
        marketing_update.setOnClickListener {
            val client = OkHttpClient()
            val marketJson = JSONObject()

            val JSON = MediaType.get("application/json; charset=utf-8")
            marketJson.put("email", com_email.isChecked)
            marketJson.put("phone", com_phone.isChecked)
            marketJson.put("userId", JSONObject(MyAccount.userData).getString("pk"))
            marketJson.put("sms", com_sms.isChecked)
            marketJson.put("postalMail",com_postal.isChecked)
            marketJson.put("socialMedia",com_social.isChecked)
            val body = RequestBody.create(JSON, marketJson.toString())
            val request = Request.Builder()
                .url(BuildConfig.MARKETING)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            val marketData = response.body()!!.string()
            Log.e("matketData",marketData)
        }
        privacy_update.setOnClickListener {
            val client = OkHttpClient()
            val privacyJson = JSONObject()

            val JSON = MediaType.get("application/json; charset=utf-8")
            privacyJson.put("bonuses", bonuses.isChecked)
            privacyJson.put("vip", vip.isChecked)
            privacyJson.put("userId", JSONObject(MyAccount.userData).getString("pk"))

            val body = RequestBody.create(JSON, privacyJson.toString())
            val request = Request.Builder()
                .url(BuildConfig.PRIVACY)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            val privacyData = response.body()!!.string()
        }


    }
}