package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import kotlinx.android.synthetic.main.frag_visa.*

class VisaInfo : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_visa, container, false)
    }

    override fun onStart() {
        super.onStart()
        visa_continue.setOnClickListener {
            MyAccount.info = "visa_input"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }

    }

}