package com.app.android.ibet.activity.UserProfile.Banking

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.pages
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import kotlinx.android.synthetic.main.activity_my_account.*
import kotlinx.android.synthetic.main.frag_banking_depo.*

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