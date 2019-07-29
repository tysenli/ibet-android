package com.app.android.ibet.activity.UserProfile.Transactions.WithdrawMethod

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.Transactions.Deposit
import com.app.android.ibet.activity.UserProfile.Transactions.Total
import kotlinx.android.synthetic.main.activity_success.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class SuccessWithdraw: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_success)
        dep_amount.text = "Withdraw " + intent.getStringExtra("amount") + " completed"
        val request = Request.Builder()
            .header("Authorization", "Token "+ Login.token)
            .url(BuildConfig.USER)
            .build()
        val response = OkHttpClient().newCall(request).execute()

        var jsonData = response.body()!!.string()

        MyAccount.amt = JSONObject(jsonData).getString("main_wallet")
        done_depo.setOnClickListener {
            startActivity(Intent(this, MyAccount::class.java))
        }
        check_balance.setOnClickListener {
            startActivity(Intent(this, Total::class.java))

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        title = "Withdraw"
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

        MyAccount.amtShow = rootView.findViewById(R.id.balance_icon)
        MyAccount.amtShow.text = MyAccount.amt.split(".")[0]
        MyAccount.amtShow.setOnClickListener {
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