package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_verify.*

class Verify: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_verify)
        via_email.setOnClickListener {
            val res = Intent(applicationContext, VeriEmail::class.java)
            res.putExtra("user", intent.getStringExtra(emailAuthP2.USER))
            res.putExtra("email",intent.getStringExtra(emailAuthP1.MAIL))
            startActivity(res)


        }
        via_phone.setOnClickListener {
            val res = Intent(applicationContext, VeriPhone::class.java)
            res.putExtra("user", intent.getStringExtra(emailAuthP2.USER))
            res.putExtra("email",intent.getStringExtra(emailAuthP1.MAIL))
            startActivity(res)
            //startActivity(Intent(applicationContext, VeriPhone::class.java))

        }
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

            else -> return super.onOptionsItemSelected(item)
        }

    }
}