package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.Banking.Deposit
import com.app.android.ibet.activity.UserProfile.Banking.Total
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.depo_amt
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import kotlinx.android.synthetic.main.activity_success.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class Success : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_success, container, false)
    }

    override fun onStart() {
        super.onStart()
        dep_amount.text = "Deposit " + depo_amt + " completed"

        val request = Request.Builder()
            .header("Authorization", "Token "+ Login.token)
            .url(BuildConfig.USER)
            .build()
        val response = OkHttpClient().newCall(request).execute()

        var jsonData = response.body()!!.string()

        MyAccount.amt = JSONObject(jsonData).getString("main_wallet")
        done_depo.setOnClickListener {
            info = "deposit"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)

        }
        check_balance.setOnClickListener {
            info = "check_bnc"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)


        }
    }
    /*
    : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_success)
        dep_amount.text = "Deposit " + intent.getStringExtra("amount") + " completed"
        val request = Request.Builder()
            .header("Authorization", "Token "+ Login.token)
            .url(BuildConfig.USER)
            .build()
        val response = OkHttpClient().newCall(request).execute()

        var jsonData = response.body()!!.string()

        MyAccount.amt = JSONObject(jsonData).getString("main_wallet")
        done_depo.setOnClickListener {
            info = "deposit"
            startActivity(Intent(this, MyAccount::class.java))
        }
        check_balance.setOnClickListener {
            info = "deposit"
            startActivity(Intent(this, MyAccount::class.java))

        }
    } */

}