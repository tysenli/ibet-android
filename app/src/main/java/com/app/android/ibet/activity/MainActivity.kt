package com.app.android.ibet.activity

import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem


import com.app.android.ibet.R
import com.app.android.ibet.fragment.*
//import com.example.proj2.ibet.fragment.PlayList

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.amtShow
import com.app.android.ibet.activity.UserProfile.Transactions.Deposit
//import com.app.android.ibet.activity.UserProfile.UserProfile
import com.zhangke.zlog.ZLog


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //private actionBar:ActionBar
    companion object {
        var isLogin = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.frag_placeholder, Display(), "HOME_FRAG")
        ft.commit()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.menu)
        //toolbar.setTitleTextColor(Color.RED)

        nav_view.setNavigationItemSelectedListener(this)
        /*
        val filePath = Environment.getExternalStorageDirectory().toString() + "/logcat.txt"
        println(filePath)
        Runtime.getRuntime().exec(arrayOf("logcat","-f",filePath,"MyAppTAG:V", "*:S")) */
        ZLog.Init(String.format("%s/log/", getExternalFilesDir(null).getPath()))
        //ZLog.e("TAG", "Internet Error");
        /*
        on_board.setOnClickListener {
            startActivity(Intent(applicationContext, IntroOne::class.java))
        }*/
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (!isLogin) {
            menu!!.findItem(R.id.logged).isVisible = false
            menu.findItem(R.id.login).isVisible = true
            val menuItem = menu.findItem(R.id.deposit)
            val rootView = menuItem.actionView

            amtShow = rootView.findViewById(R.id.balance_icon)
            amtShow.setOnClickListener {
                startActivity(Intent(this, Signup::class.java))
            }

        } else {
            menu!!.findItem(R.id.logged).isVisible = true
            menu.findItem(R.id.login).isVisible = false
            val menuItem = menu.findItem(R.id.deposit)
            val rootView = menuItem.actionView

            amtShow = rootView.findViewById(R.id.balance_icon)
            amtShow.text = MyAccount.amt.split(".")[0]
            amtShow.setOnClickListener {
                startActivity(Intent(this, Deposit::class.java))
            }
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
            R.id.deposit -> {
                startActivity(Intent(this, Signup::class.java))
                return true
            }
            R.id.login -> {
                /*
                val fm = supportFragmentManager

                // add
                val ft = fm.beginTransaction()
                //ft.remove(fm.findFragmentById(R.id.frag_placeholder)!!)
                ft.replace(R.id.frag_placeholder, Login(this@MainActivity), "FAVORITES_FRAG")
                ft.commit() */
                startActivity(Intent(this, Login::class.java))
                return true
            }
            R.id.logged -> {
                startActivity(Intent(this, MyAccount::class.java))
                return true
            }
            /*
            R.id.chinese -> {
                val locale = Locale("zh")
                val config = Configuration()
                config.locale = locale
                baseContext.resources.updateConfiguration(
                    config,
                    baseContext.resources.displayMetrics

                )
                startActivity(Intent( this,MainActivity::class.java))
                return true
            }
            R.id.english -> {
                val locale1 = Locale("en")//fr is the code for french language
                Locale.setDefault(locale1)
                val config1 = Configuration()
                config1.locale = locale1
                baseContext.resources.updateConfiguration(
                    config1,
                    baseContext.resources.displayMetrics
                )
                startActivity(Intent( this,MainActivity::class.java))
                return true
            }
            */
            /*
            R.id.user -> {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                //ft.remove(fm.findFragmentById(R.id.frag_placeholder)!!)
                ft.replace(R.id.frag_placeholder, UserInfo(this@MainActivity), "FAVORITES_FRAG")
                ft.commit()
                return true
            } */
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {
        /*
            R.id.home -> {
                // Handle the camera action
                val fm = supportFragmentManager

                // add
                val ft = fm.beginTransaction()
                title = "ibet"
                //ft.remove(fm.findFragmentById(R.id.frag_placeholder)!!)
                ft.replace(R.id.frag_placeholder, Home(), "FAVORITES_FRAG")
                ft.commit()
            } */
            R.id.sports -> {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, Sports(this@MainActivity), "SPORT_FRAG")
                ft.commit()
                //Sports(this)
            }

            R.id.live_casino -> {
                //title ="Live Casino"
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder,Casino(this@MainActivity),"LIVE_FRAG")
                ft.commit()
                //Casino(this)
            }
            R.id.slots -> {
                //title = "Slots"

                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, Slots(this@MainActivity), "SLOT_FRAG")
                ft.commit()
                //Slots(this)
            }
            R.id.lottery -> {
                //title = "Lottery"
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, Lottery(this@MainActivity), "LOTTERY_FRAG")
                ft.commit()
                //Lottery(this)

            }
            R.id.cookie -> {

                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, Cookie(), "cookie")
                ft.commit()

            }
            R.id.privacy -> {

                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, Privacy(), "privacy")
                ft.commit()

            }
            R.id.terms -> {

                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, Terms(), "terms")
                ft.commit()

            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

