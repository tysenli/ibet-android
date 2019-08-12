package com.app.android.ibet.activity.UserProfile.Banking

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.amtShow
import com.app.android.ibet.activity.UserProfile.Banking.DepositMethod.*
import kotlinx.android.synthetic.main.frag_banking_depo.*

class Deposit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.frag_banking_depo)
        paypal.setOnClickListener {
            startActivity(Intent(this, Paypal::class.java))
        }
        wechat.setOnClickListener {
            startActivity(Intent(this, QaiWechat::class.java))
        }
        ali.setOnClickListener {
            startActivity(Intent(this, QaiAli::class.java))
        }
        line_pay.setOnClickListener {
            startActivity(Intent(this, LinePay::class.java))
        }
        visa.setOnClickListener {
            startActivity(Intent(this, VisaInfo::class.java))
        }
        jdpay.setOnClickListener {
            startActivity(Intent(this, JDPay::class.java))
        }
        unionpay.setOnClickListener {
            startActivity(Intent(this, UnionPay::class.java))
        }
        bank.setOnClickListener {
            startActivity(Intent(this, BankDep::class.java))
        }
        quickpay.setOnClickListener {
            startActivity(Intent(this, QuickPay::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        title = "Deposit Method"
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (!MainActivity.isLogin) {
            menu!!.findItem(R.id.logged).isVisible = false
            menu.findItem(R.id.login).isVisible = true
        } else {
            menu!!.findItem(R.id.logged).isVisible = true
            menu.findItem(R.id.login).isVisible = false
        }
        val menuItem = menu.findItem(R.id.deposit)
        val rootView = menuItem.actionView

        amtShow = rootView.findViewById(R.id.balance_icon)
        amtShow.text = MyAccount.amt.split(".")[0]
        amtShow.setOnClickListener {
            startActivity(Intent(this, Deposit::class.java))
        }
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            android.R.id.home -> {
                // startActivity(Intent(this, MyAccount::class.java))
                onBackPressed()
                return true
            }
            R.id.deposit -> {
                startActivity(Intent(this, Signup::class.java))
                return true
            }
            R.id.login -> {
                startActivity(Intent(this, Login::class.java))
                return true
            }
            R.id.logged -> {
                startActivity(Intent(this, MyAccount::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }


    }
}