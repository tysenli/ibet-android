package com.app.android.ibet.activity.UserProfile


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.fragment.Live
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_bets.*

class Bets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bets)
        val adapter = FragmentPagerItemAdapter(
            supportFragmentManager, FragmentPagerItems.with(this)
                .add("OPEN", Open().javaClass)
                .add("CASH OUT",CashOut().javaClass)
                .add("SETTLED",Settled().javaClass)
                .create()
        )

        bet_viewpager.adapter = adapter
        bets_viewpagertab.setViewPager(bet_viewpager)

        bets_viewpagertab.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val tab = bets_viewpagertab.getTabAt(position)


            }
        })
        bet_viewpager.setOnTouchListener(object : View.OnTouchListener {
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
        return super.onPrepareOptionsMenu(menu)
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







