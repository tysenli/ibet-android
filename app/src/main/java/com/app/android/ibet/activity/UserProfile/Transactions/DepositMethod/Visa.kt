package com.app.android.ibet.activity.UserProfile.Transactions.DepositMethod

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.Transactions.Deposit
import kotlinx.android.synthetic.main.activity_amount_input.*

class Visa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_input)
        change_method.setOnClickListener {
            startActivity(Intent(this, Deposit::class.java))

        }
    }
}