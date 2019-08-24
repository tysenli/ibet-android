package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_one_click.*

class oneClick : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_click)
        info.text = "USERNAME:          " + intent.getStringExtra("username") + "\n" + "PASSWORD:          " + intent.getStringExtra("password")
        done.setOnClickListener {
            startActivity(Intent(applicationContext, oneClickIntro::class.java))
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