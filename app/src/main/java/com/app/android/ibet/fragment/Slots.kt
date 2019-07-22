package com.app.android.ibet.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.fragment_slots.*
import kotlinx.android.synthetic.main.fragment_sports.*

@SuppressLint("ValidFragment")
class Slots (context: Context): Fragment() {
    private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_slots, container, false)
    }
    override fun onStart() {
        super.onStart()

        val adapter = FragmentPagerItemAdapter(
            fragmentManager, FragmentPagerItems.with(parentContext)
                .add("TOP RATED", Live().javaClass)
                .add("NEW",Live().javaClass)
                .add("SLOTS",Live().javaClass)
                .add("JACKPOTS",Live().javaClass)
                .add("TABLE GAMES", Live().javaClass)
                .add("VIRTUAL SPORTS", Live().javaClass)
                .add("OTHER GAMES", Live().javaClass)
                .create()
        )
        slots_viewpager.adapter = adapter
        slots_viewpagertab.setViewPager(slots_viewpager)

        slots_viewpagertab.setOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val tab = slots_viewpagertab.getTabAt(position)


            }
        })
        slots_viewpager.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                return false
            }
        })
    }

}