package com.app.android.ibet.activity.UserProfile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import com.app.android.ibet.activity.Signup.oneClickIntro
import kotlinx.android.synthetic.main.frag_deposit.*

class Deposit : Fragment() {
    private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_deposit, container, false)
    }

    override fun onStart() {
        super.onStart()
        paypal.setOnClickListener {
            startActivity(Intent(activity, Paypal::class.java))

        }
        wechat.setOnClickListener {
            startActivity(Intent(activity, Wechat::class.java))

        }
    }
}