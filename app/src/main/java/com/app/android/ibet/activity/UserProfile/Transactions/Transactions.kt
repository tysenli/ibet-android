package com.app.android.ibet.activity.UserProfile.Transactions

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.frag_transactions.*

class Transactions : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_transactions, container, false)
    }

    override fun onStart() {
        super.onStart()
        deposit.setOnClickListener {
            startActivity(Intent(activity, Deposit::class.java))

        }
    }
}