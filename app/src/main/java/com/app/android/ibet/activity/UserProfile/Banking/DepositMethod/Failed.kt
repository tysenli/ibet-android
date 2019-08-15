package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.UserProfile.MyAccount
import kotlinx.android.synthetic.main.activity_success.*
import kotlinx.android.synthetic.main.frag_fail.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class Failed : Fragment() {
    //private var parentContext = context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_fail, container, false)
    }

    override fun onStart() {
        super.onStart()

        try_again.setOnClickListener {
            MyAccount.info = "deposit"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)

        }

    }
}