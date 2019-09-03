package com.app.android.ibet.activity.UserProfile.Banking.WithdrawMethod

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.Banking.Deposit
import com.app.android.ibet.activity.UserProfile.Banking.Total
import kotlinx.android.synthetic.main.activity_success.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class SuccessWithdraw: Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_success, container, false)
    }

    override fun onStart() {
        super.onStart()
        dep_amount.text = "Withdraw " + MyAccount.with_amt + " completed"

        val request = Request.Builder()
            .header("Authorization", "Token "+ Login.token)
            .url(BuildConfig.USER)
            .build()
        val response = OkHttpClient().newCall(request).execute()

        var jsonData = response.body()!!.string()

        MyAccount.amt = JSONObject(jsonData).getString("main_wallet")
        done_depo.setOnClickListener {
            MyAccount.info = "withdraw"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)

        }
        check_balance.setOnClickListener {
            MyAccount.info = "check_bnc"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)


        }
    }


}