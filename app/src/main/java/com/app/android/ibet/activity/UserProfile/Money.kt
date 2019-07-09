package com.app.android.ibet.activity.UserProfile

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import com.app.android.ibet.R
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_bets.*
import kotlinx.android.synthetic.main.activity_money.*

class Money : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money)
        val adapter = FragmentPagerItemAdapter(
            supportFragmentManager, FragmentPagerItems.with(this)
                .add("DEPOSIT", Deposit().javaClass)
                .add("WITHDRAW",Withdraw().javaClass)
                .create()
        )

        money_viewpager.adapter = adapter
        money_viewpagertab.setViewPager(money_viewpager)

        money_viewpagertab.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val tab = money_viewpagertab.getTabAt(position)


            }
        })
        money_viewpager.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                return false
            }
        })
    }
}