package com.app.android.ibet.activity.UserProfile


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import com.app.android.ibet.R
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
}







