package com.app.android.ibet.activity.UserProfile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.app.android.ibet.R

class Promotion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_promotion)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}