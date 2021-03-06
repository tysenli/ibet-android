package com.app.android.ibet.activity.UserProfile.Banking

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import kotlinx.android.synthetic.main.frag_banking_depo.*
import kotlinx.android.synthetic.main.frag_banking_depo.deposit
import kotlinx.android.synthetic.main.frag_banking_depo.withdraw
import kotlinx.android.synthetic.main.frag_banking_with.*

class BankingWith : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.app.android.ibet.R.layout.frag_banking_with, container, false)
    }

    override fun onStart() {
        super.onStart()
        deposit.setOnClickListener {
            info = "deposit"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
            //startActivity(Intent(activity, Deposit::class.java))

        }
        withdraw.setOnClickListener {
            info = "withdraw"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
            //startActivity(Intent(activity, Withdraw::class.java))

        }
        bank_with.setOnClickListener {
            info = "bankwith"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        /*
        total.setOnClickListener {
            startActivity(Intent(activity, Total::class.java))

        } */
    }
}