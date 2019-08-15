package com.app.android.ibet.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.fragment_sports.*

@SuppressLint("ValidFragment")
class Sports (context: Context): Fragment() {
    private var parentContext = context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_sports, container, false)
        }

    override fun onStart() {
        super.onStart()
        //val fragmentAdapter = MyPagerAdapter(context!!, fragmentManager!!)
        //viewpager_main.adapter = fragmentAdapter

        //tabs_main.setupWithViewPager(viewpager_main)
        /*
        val adapter = ViewPagerItemAdapter(ViewPagerItems.with(parentContext)
            .add("LIVE",R.layout.frag_live)
            .add("ssss",R.layout.frag_live)
            .add("kjjk",R.layout.frag_live)
            .create())
            */

        val adapter = FragmentPagerItemAdapter(
            fragmentManager,FragmentPagerItems.with(parentContext)
                .add("LIVE",GameLobbySlots().javaClass).create()
        )
        sports_viewpager.adapter = adapter
        sports_viewpagertab.setViewPager(sports_viewpager)

        fun onPageSelected(position:Int) {
            val page = adapter.getPage(position)
        }

    }


    /*

    class MyPagerAdapter(context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val parentContext = context

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                     Live(parentContext)//topTrack home page
                }

                else ->
                    Football(parentContext)//playlist
            }
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> "Live"
                1 -> "Football"
                2 -> "Basketball"
                3 -> "Tennis"
                //4 -> "Ice Hockey"
               // 5 -> "Golf"
               // 6 -> "Volleyball"
               // 7 -> "Baseball"
               // 8 -> "UFC / MMA"
               // 9 -> "American Football"
                else -> {
                    return "All sports"
                }
            }
        }

    } */

}