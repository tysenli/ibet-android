package com.app.android.ibet.activity.UserProfile.Account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login.Companion.token
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.MainActivity.Companion.isLogin
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.frag_account.*
import org.json.JSONObject

class Account : Fragment() {
    private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_account, container, false)
    }
    override fun onStart() {
        super.onStart()
        var userData = Api().get(BuildConfig.USER)
        //println(userData)
        acc_first_name.text = JSONObject(userData).getString("first_name")
        acc_last_name.text = JSONObject(userData).getString("last_name")
        acc_username.text = JSONObject(userData).getString("username")
        acc_email.text = JSONObject(userData).getString("email")
        acc_address.text= JSONObject(userData).getString("street_address_1") + ", " +
                JSONObject(userData).getString("city") + ", " + JSONObject(userData).getString("country")+
                "," + JSONObject(userData).getString("zipcode")
        acc_phone.text = JSONObject(userData).getString("phone")
        acc_edit.setOnClickListener {
            info = "acc_edit"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        logout.setOnClickListener {
            isLogin = false
            token = ""
            startActivity(Intent(activity, MainActivity::class.java))
            activity!!.overridePendingTransition(0, 0)
        }

    }

}