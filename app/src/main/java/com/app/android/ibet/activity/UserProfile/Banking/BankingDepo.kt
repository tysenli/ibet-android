package com.app.android.ibet.activity.UserProfile.Banking

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import kotlinx.android.synthetic.main.frag_banking_depo.*
import kotlinx.android.synthetic.main.frag_banking_depo.visa


class BankingDepo : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_banking_depo, container, false)
    }

    override fun onStart() {
        super.onStart()
        deposit.background = resources.getDrawable(R.color.btn_d)
        withdraw.background = resources.getDrawable(R.color.btn_l)
        transfer.background = resources.getDrawable(R.color.btn_l)
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
        qai_wechat.setOnClickListener {

            info = "qai_wechat"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        qai_ali.setOnClickListener {
            info = "qai_ali"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        qai_bt.setOnClickListener {
            info = "qai_bank"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        qai_union.setOnClickListener {
            info = "qai_union"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        asia_wechat.setOnClickListener {
            info = "asia_wechat"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        asia_alipay.setOnClickListener {
            info = "asia_ali"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        asia_jdpay.setOnClickListener {
            info = "jdpay"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        visa.setOnClickListener {
            info = "visainfo"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        asia_quickpay.setOnClickListener {
            info = "quickpay"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        asia_unionpay.setOnClickListener {
            info = "unionpay"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        asia_bank.setOnClickListener {
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