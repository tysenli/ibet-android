package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.Banking.Deposit
import kotlinx.android.synthetic.main.activity_amount_input.*
import kotlinx.android.synthetic.main.frag_visa.*

class AstropayInfo: Fragment() {
    //private var parentContext = context
    companion object {
        var cardnum = ""
        var carddate = ""
        var cvv = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_visa, container, false)
    }

    override fun onStart() {
        super.onStart()
        visa_continue.setOnClickListener {
            cardnum = card_num.text.toString()
            carddate = card_time.text.toString()
            cvv = card_code.text.toString()
            MyAccount.info = "astropay_input"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }

    }

}
