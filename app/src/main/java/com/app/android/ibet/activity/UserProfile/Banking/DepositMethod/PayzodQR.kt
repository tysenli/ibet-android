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


class PayzodQR : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qrcode)
        val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width = point.x
        val height = point.y
        var smallerDimension = if (width < height) width else height

        //println(intent.getStringExtra("QRcode").trim())
        val qrgEncoder = QRGEncoder("222", null, QRGContents.Type.TEXT,smallerDimension * 3 / 4)
        val bitmap = qrgEncoder.encodeAsBitmap()
        payzodQR_img.setImageBitmap(bitmap)

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