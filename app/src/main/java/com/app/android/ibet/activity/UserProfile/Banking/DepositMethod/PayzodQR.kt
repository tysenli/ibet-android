package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import com.app.android.ibet.R
import android.R.attr.x
import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import kotlinx.android.synthetic.main.qrcode.*
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.R.id
import android.content.Intent
import com.app.android.ibet.activity.UserProfile.MyAccount


class PayzodQR : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qrcode)
        val base = intent.getStringExtra("QRcode")
        val imageAsBytes = Base64.getDecoder().decode(base)
        payzodQR_img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size))
        payzod_continue.setOnClickListener {
            MyAccount.info = "deposit"
            val intent = Intent(this, MyAccount::class.java)
            startActivity(intent)
            this!!.overridePendingTransition(0, 0)
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