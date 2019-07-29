package com.app.android.ibet.activity.UserProfile

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.Login.Login.Companion.token
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.Transactions.Deposit
import com.app.android.ibet.activity.UserProfile.Transactions.Total
import com.app.android.ibet.activity.UserProfile.Transactions.Transactions
import com.app.android.ibet.api.Api
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_bets.*
import kotlinx.android.synthetic.main.activity_my_account.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MyAccount : AppCompatActivity() {
    companion object {
        var amt = ""
        lateinit var  amtShow : Button
        lateinit var userData :String
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        val request = Request.Builder()
            .header("Authorization", "Token "+ token)
            .url(BuildConfig.USER)
            .build()
        val response = OkHttpClient().newCall(request).execute()

        var jsonData = response.body()!!.string()

        amt = JSONObject(jsonData).getString("main_wallet")
        val adapter = FragmentPagerItemAdapter(
            supportFragmentManager, FragmentPagerItems.with(this)
                .add("Transactions", Transactions().javaClass)
                .add("History",Transactions().javaClass)
                .add("Account",Transactions().javaClass)
                .add("Affiliates",Transactions().javaClass)
                .create()
        )

        account_viewpager.adapter = adapter
        account_pagertab.setViewPager(account_viewpager)

        account_pagertab.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val tab = account_pagertab.getTabAt(position)


            }
        })
        account_viewpager.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                return false
            }
        })

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        amtShow.text = amt.split(".")[0]
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

