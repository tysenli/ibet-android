package com.app.android.ibet.activity

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog


import com.app.android.ibet.R
import com.app.android.ibet.fragment.*
//import com.example.proj2.ibet.fragment.PlayList

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.Navigation.MenuExpandableAdapter
import com.app.android.ibet.activity.Navigation.NaviMenuItem
import com.app.android.ibet.activity.Navigation.SampleMenu
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.amtShow
import com.app.android.ibet.activity.UserProfile.Banking.Deposit
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.loginShow
import com.app.android.ibet.fragment.Display
//import com.app.android.ibet.activity.UserProfile.UserProfile
import com.zhangke.zlog.ZLog
import kotlinx.android.synthetic.main.login_actionlayout.*
import android.widget.Toast
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.activity.UserProfile.ResponsibleGame.ResponsibleGame
import com.app.android.ibet.activity.UserProfile.ResponsibleGame.ResponsibleGame.Companion.remindTime

import com.app.android.ibet.api.URLs
import okhttp3.OkHttpClient
import okhttp3.Request

import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.activity_login.*

import org.json.JSONObject


class MainActivity : AppCompatActivity(), MenuExpandableAdapter.OnMenuItemClick {
    //private actionBar:ActionBar
    private lateinit var navigationBody: ExpandableListView
    private lateinit var navigationBodyAdapter: MenuExpandableAdapter

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

        navigationBody = findViewById(R.id.navigation_body)

        navigationBodyAdapter = MenuExpandableAdapter(this)
        navigationBodyAdapter.setOnMenuItemClickListener(this)

        navigationBody.setAdapter(navigationBodyAdapter)

        navigationBody.setOnGroupClickListener(navigationBodyAdapter)
        navigationBody.setOnChildClickListener(navigationBodyAdapter)

        navigationBodyAdapter.showItems(SampleMenu.getMenu())

        Log.e("time",ResponsibleGame.remindTime.toString())
        if (ResponsibleGame.remindTime != 0) {
            val timer = Timer()
            val hourlyTask = object : TimerTask() {

                override fun run() {
                    // your code here...
                    this@MainActivity.runOnUiThread(Runnable {
                        val toast = Toast.makeText(
                            this@MainActivity,
                            "Alert...", Toast.LENGTH_SHORT
                        )
                        toast.show()
                        Log.e("alert", "alert")
                    })
                }
            }


            // schedule the task to run starting now and then every hour...
            var time = 60
            when (remindTime) {
                 0 -> time = 5
                 1 -> time = 30
                 2 -> time = 60
                 3 -> time = 120
            }
            timer.schedule(
                hourlyTask,
                0L,
                1000 * time * 60.toLong()
            )// 1000*10*60 every 10 minutes
        }




           /*
        val filePath = Environment.getExternalStorageDirectory().toString() + "/logcat.txt"
        println(filePath)
        Runtime.getRuntime().exec(arrayOf("logcat","-f",filePath,"MyAppTAG:V", "*:S"))
        ZLog.Init(String.format("%s/log/", getExternalFilesDir(null).getPath()))
        //ZLog.e("TAG", "Internet Error");

        on_board.setOnClickListener {
            startActivity(Intent(applicationContext, IntroOne::class.java))
        }*/
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (!isLogin) {
            menu!!.findItem(R.id.logged).isVisible = false
            menu.findItem(R.id.login).isVisible = true
            menu.findItem(R.id.deposit).isVisible = false
            menu.findItem(R.id.notification).isVisible = false
            val menuItem = menu.findItem(R.id.login)
            val rootView = menuItem.actionView
            loginShow = rootView.findViewById(R.id.login_btn)
            loginShow.setOnClickListener {
                startActivity(Intent(this, Login::class.java))
                overridePendingTransition(0, 0)
            }
            /*
            amtShow = rootView.findViewById(R.id.balance_icon)
            amtShow.setOnClickListener {
                startActivity(Intent(this, Signup::class.java))
                overridePendingTransition(0, 0)
            } */

        } else {
            menu!!.findItem(R.id.logged).isVisible = true
            menu.findItem(R.id.login).isVisible = false
            menu.findItem(R.id.deposit).isVisible = true
            menu.findItem(R.id.notification).isVisible = false
            val menuItem = menu.findItem(R.id.deposit)
            val rootView = menuItem.actionView

            val client = OkHttpClient()
            val request = Request.Builder()
                .url(URLs.USER_INBOX_UNREAD + JSONObject(MyAccount.userData).getString("pk"))
                .build()

            val response = client.newCall(request).execute()
            val notiCnt = response.body()!!.string()
            Log.e("cnt",notiCnt)
            if (notiCnt.toInt() > 0) {
                menu.findItem(R.id.notification).isVisible = true
                menu!!.findItem(R.id.logged).isVisible = false
                menu.findItem(R.id.login).isVisible = false
                menu.findItem(R.id.deposit).isVisible = true
                val menuItem = menu.findItem(R.id.notification)
                val rootView = menuItem.actionView

                val button= rootView.findViewById<Button>(R.id.notification_cnt)
                button.text = notiCnt

                val notiImg= rootView.findViewById<ImageView>(R.id.noti_img)
                notiImg.setOnClickListener {
                    info = "deposit"
                    startActivity(Intent(this, MyAccount::class.java))
                    overridePendingTransition(0, 0)

                }

            }

            amtShow = rootView.findViewById(R.id.balance_icon)
            amtShow.text = MyAccount.amt.split(".")[0]

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

    override fun onMenuClick(position: Int, menuItem: NaviMenuItem) {
        when (position) {
            0 -> {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, Sports(this), "sports")
                ft.commit()
            }
            1 -> {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, Casino(this), "casino")
                ft.commit()
            }
            2 -> {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, Slots(this), "casino")
                ft.commit()
            }
            3 -> {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.frag_placeholder, Lottery(this), "casino")
                ft.commit()
            }

        }

        //Toast.makeText(this, "Click on Menu ${menuItem.title}", Toast.LENGTH_LONG).show()
    }

    override fun onSubMenuClick(position: Int, menuItem: NaviMenuItem) {
        Toast.makeText(this, "Click on SubMenu ${menuItem.title}", Toast.LENGTH_LONG).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        when (item.itemId) {
            /*
            R.id.deposit -> {
                startActivity(Intent(this, Signup::class.java))
                return true
            } */

            R.id.login -> {

                startActivity(Intent(this, Login::class.java))
                overridePendingTransition(0, 0)
                return true
            }
            R.id.notification -> {
                info = "deposit"
                startActivity(Intent(this, MyAccount::class.java))
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

            /*
            R.id.user -> {
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                //ft.remove(fm.findFragmentById(R.id.frag_placeholder)!!)
                ft.replace(R.id.frag_placeholder, UserInfo(this@MainActivity), "FAVORITES_FRAG")
                ft.commit()
                return true
            } */


    /*

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


        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }  */
}



