package com.app.android.ibet.activity.UserProfile.Banking

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.Banking.DepositMethod.VisaInfo
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.adapter
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.pages
import com.ogaclejapan.smarttablayout.SmartTabLayout
import kotlinx.android.synthetic.main.frag_banking_depo.*
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import kotlinx.android.synthetic.main.activity_my_account.*
import kotlinx.android.synthetic.main.frag_banking_depo.visa


class BankingDepo : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_banking_depo, container, false)
    }

    override fun onStart() {
        super.onStart()
        deposit.setOnClickListener {
            info = "deposit"
            val intent = Intent(activity, MyAccount::class.java)
            startActivity(intent)
            activity!!.overridePendingTransition(0, 0)


        }
        withdraw.setOnClickListener {
            info = "withdraw"
            val intent = Intent(activity, MyAccount::class.java)
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
            //startActivity(Intent(activity, Withdraw::class.java))


        }
        wechat.setOnClickListener {

            info = "wechat"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        ali.setOnClickListener {
            info = "ali"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        jdpay.setOnClickListener {
            info = "jdpay"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        visa.setOnClickListener {
            info = "visainfo"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        quickpay.setOnClickListener {
            info = "quickpay"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        unionpay.setOnClickListener {
            info = "unionpay"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        bank.setOnClickListener {
            info = "online"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        astropay.setOnClickListener {
            info = "astropayinfo"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        fgate.setOnClickListener {
            info = "fgate"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        help2pay.setOnClickListener {
            info = "help2pay"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        ciclepay.setOnClickListener {
            info = "ciclepay"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        payzod.setOnClickListener {
            info = "payzod"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        scratch.setOnClickListener {
            info = "scratch"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)

        }

        /*
        total.setOnClickListener {
            startActivity(Intent(activity, Total::class.java))

        } */
    }
}