package com.app.android.ibet.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem


import com.app.android.ibet.R
import com.app.android.ibet.activity.OnBoarding.IntroOne
import com.app.android.ibet.fragment.*
//import com.example.proj2.ibet.fragment.PlayList

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //private actionBar:ActionBar
    companion object {
        var isLogin = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        actionBar = actionBar
        val titleId = Resources.getSystem().getIdentifier(
            "action_bar_title",
            "id", "android"
        )
        tvTitle =  findViewById<TextView>(titleId)
        tvTitle.gravity = Gravity.CENTER
        */
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        on_board.setOnClickListener {
            startActivity(Intent(applicationContext, IntroOne::class.java))
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (!isLogin) {
            menu!!.findItem(R.id.user).isVisible = false
            menu.findItem(R.id.login).isVisible = true
        } else {
            menu!!.findItem(R.id.user).isVisible = true
            menu.findItem(R.id.login).isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.login -> {
                val fm = supportFragmentManager

                // add
                val ft = fm.beginTransaction()
                //ft.remove(fm.findFragmentById(R.id.frag_placeholder)!!)
                ft.replace(R.id.frag_placeholder, Login(this@MainActivity), "FAVORITES_FRAG")
                ft.commit()
                return true
            }
            R.id.user -> {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                //ft.remove(fm.findFragmentById(R.id.frag_placeholder)!!)
                ft.replace(R.id.frag_placeholder, UserInfo(this@MainActivity), "FAVORITES_FRAG")
                ft.commit()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.home -> {
                // Handle the camera action
                val fm = supportFragmentManager

                // add
                val ft = fm.beginTransaction()
                title = "ibet"
                //ft.remove(fm.findFragmentById(R.id.frag_placeholder)!!)
                ft.replace(R.id.frag_placeholder, Home(this@MainActivity), "FAVORITES_FRAG")
                ft.commit()
            }

            R.id.sportsbook_a -> {
                title = "Sportsbook A"
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, SportsA(this@MainActivity), "FAVORITES_FRAG")
                ft.commit()
            }
            R.id.sportsbook_b -> {
                title = "Sportsbook B"
            }
            R.id.sportsbook_c -> {
                title = "Sportsbook C"
            }
            R.id.live_casino -> {
                title ="Live Casino"
            }
            R.id.game -> {
                title = "Games"
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, Games(this@MainActivity), "FAVORITES_FRAG")
                ft.commit()
            }
            R.id.lottery -> {
                title = "Lottery"

            }
            R.id.language -> {
                val fm = supportFragmentManager
                // add
                val ft = fm.beginTransaction()
                title = "Language"
                //ft.remove(fm.findFragmentById(R.id.frag_placeholder)!!)
                ft.replace(R.id.frag_placeholder, Language(this@MainActivity), "FAVORITES_FRAG")
                ft.commit()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

