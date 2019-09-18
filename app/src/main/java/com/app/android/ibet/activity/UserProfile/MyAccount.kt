package com.app.android.ibet.activity.UserProfile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
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
import com.app.android.ibet.activity.UserProfile.Account.Account
import com.app.android.ibet.activity.UserProfile.Account.EditAcc
import com.app.android.ibet.activity.UserProfile.Analysis.*
import com.app.android.ibet.activity.UserProfile.Banking.Deposit
import com.app.android.ibet.activity.UserProfile.Banking.BankingDepo
import com.app.android.ibet.activity.UserProfile.Banking.BankingWith
import com.app.android.ibet.activity.UserProfile.Banking.DepositMethod.*
import com.app.android.ibet.activity.UserProfile.Banking.WithdrawMethod.QaiBankWith
import com.app.android.ibet.activity.UserProfile.Banking.WithdrawMethod.SuccessWithdraw
import com.app.android.ibet.activity.UserProfile.Banking.WithdrawMethod.WithdrawPass
import com.app.android.ibet.activity.UserProfile.ResponsibleGame.Lock
import com.app.android.ibet.activity.UserProfile.ResponsibleGame.ResponsibleGame
import com.app.android.ibet.activity.UserProfile.Setting.Setting
import com.app.android.ibet.api.URLs
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_my_account.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MyAccount : AppCompatActivity() {
    companion object {
        var amt = ""
        lateinit var  amtShow : Button
        lateinit var loginShow : Button
        lateinit var userData :String
        lateinit var pages : FragmentPagerItems
        lateinit var adapter: FragmentPagerItemAdapter
        var info = "deposit"
        var depo_amt = ""
        var with_amt = ""
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
            .url(URLs.USER)
            .build()
        val response = OkHttpClient().newCall(request).execute()

        var jsonData = response.body()!!.string()
        Log.e("user",jsonData)
        amt = JSONObject(jsonData).getString("main_wallet")

        pages =  FragmentPagerItems.with(this)
                .add("Banking", BankingDepo().javaClass)
                .add("Analysis",Analysis().javaClass)
                .add("Account",Account().javaClass)
                .add("Responsible Gaming",ResponsibleGame().javaClass)
                .add("Settings", Setting().javaClass)
                .create()
        when (info) {
            "deposit"    -> pages[0] = FragmentPagerItem.of("Banking", BankingDepo().javaClass)
            "withdraw"   -> pages[0] = FragmentPagerItem.of("Banking", BankingWith().javaClass)
            "visainfo"   -> pages[0] = FragmentPagerItem.of("Banking", VisaInfo().javaClass)
            "visa_input" -> pages[0] = FragmentPagerItem.of("Banking", Visa().javaClass)
            "qai_ali"    -> pages[0] = FragmentPagerItem.of("Banking", QaiAli().javaClass)
            "qai_bank"   -> pages[0] = FragmentPagerItem.of("Banking", QaiBank().javaClass)
            "qai_union"  -> pages[0] = FragmentPagerItem.of("Banking", QaiUnion().javaClass)
            "qai_wechat" -> pages[0] = FragmentPagerItem.of("Banking", QaiWechat().javaClass)
            "quickpay"   -> pages[0] = FragmentPagerItem.of("Banking", AsiaQuickPay().javaClass)
            "unionpay"   -> pages[0] = FragmentPagerItem.of("Banking", AsiaUnionPay().javaClass)
            "jdpay"      -> pages[0] = FragmentPagerItem.of("Banking", AsiaJDPay().javaClass)
            "asia_ali"   -> pages[0] = FragmentPagerItem.of("Banking", AsiaAli().javaClass)
            "asia_wechat"-> pages[0] = FragmentPagerItem.of("Banking", AsiaWechat().javaClass)
            "astropayinfo"->pages[0] = FragmentPagerItem.of("Banking", AstropayInfo().javaClass)
            //"astropay_input"->pages[0] = FragmentPagerItem.of("Banking", Astropay().javaClass)
            "fgate"      -> pages[0] = FragmentPagerItem.of("Banking", Fgo().javaClass)
            "help2pay"   -> pages[0] = FragmentPagerItem.of("Banking", Help2pay().javaClass)
            "ciclepay"   -> pages[0] = FragmentPagerItem.of("Banking", Circlepay().javaClass)
            "success"    -> pages[0] = FragmentPagerItem.of("Banking", Success().javaClass)
            "success_with"->pages[0] = FragmentPagerItem.of("Banking", SuccessWithdraw().javaClass)
            "fail"       -> pages[0] = FragmentPagerItem.of("Banking", Failed().javaClass)
            "bankwith"   -> pages[0] = FragmentPagerItem.of("Banking", QaiBankWith().javaClass)
            "payzod"     -> pages[0] = FragmentPagerItem.of("Banking", Payzod().javaClass)
            "scratch"    -> pages[0] = FragmentPagerItem.of("Banking", ScratchCard().javaClass)
            "online"     -> pages[0] = FragmentPagerItem.of("Banking", AsiaBank().javaClass)
            "withdraw_pass"->pages[0] = FragmentPagerItem.of("Banking", WithdrawPass().javaClass)


            "sports"     -> pages[1] = FragmentPagerItem.of("Analysis", SportsAly().javaClass)
            "depo&with"  -> pages[1] = FragmentPagerItem.of("Analysis", DepoWithAly().javaClass)
            "slots"      -> pages[1] = FragmentPagerItem.of("Analysis", SlotsAly().javaClass)
            "casino"     -> pages[1] = FragmentPagerItem.of("Analysis", CasinoAly().javaClass)

            "acc"        -> pages[2] = FragmentPagerItem.of("Account", Account().javaClass)
            "acc_edit"   -> pages[2] = FragmentPagerItem.of("Account", EditAcc().javaClass)

            "lock_account"-> pages[3] = FragmentPagerItem.of("Responsible Game", Lock().javaClass)
            "rg"         -> pages[3] = FragmentPagerItem.of("Responsible Game",  ResponsibleGame().javaClass)

        }


        adapter = FragmentPagerItemAdapter(supportFragmentManager,pages)
        adapter.notifyDataSetChanged()


        account_viewpager.adapter = adapter


        when (info) {
            "check_bnc"  -> account_viewpager.setCurrentItem(1, true)
            "sports"     -> account_viewpager.setCurrentItem(1, true)
            "depo&with"  -> account_viewpager.setCurrentItem(1, true)
            "slots"      -> account_viewpager.setCurrentItem(1, true)
            "casino"     -> account_viewpager.setCurrentItem(1, true)

            "acc"        -> account_viewpager.setCurrentItem(2, true)
            "acc_edit"   -> account_viewpager.setCurrentItem(2, true)

            "lock_account"-> account_viewpager.setCurrentItem(3, true)
            "rg"          -> account_viewpager.setCurrentItem(3, true)

        }
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
                startActivity(Intent(this, MainActivity::class.java))
                //onBackPressed()
                overridePendingTransition(0, 0)
                return true
            }
            R.id.login -> {
                startActivity(Intent(this, Login::class.java))
                overridePendingTransition(0, 0)
                return true
            }
            R.id.logged -> {
                info = "deposit"
                startActivity(Intent(this, MyAccount::class.java))
                overridePendingTransition(0, 0)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}

