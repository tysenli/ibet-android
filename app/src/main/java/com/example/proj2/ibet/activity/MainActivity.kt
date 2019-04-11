package com.example.proj2.ibet.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


import com.example.proj2.ibet.R
import com.example.proj2.ibet.fragment.Home
import com.example.proj2.ibet.fragment.User
//import com.example.proj2.ibet.fragment.PlayList

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentAdapter = MyPagerAdapter(this, supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)
    }


    class MyPagerAdapter(context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val parentContext = context

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    Home(parentContext) // home page
                }
                1 -> {
                    Home(parentContext)

                }
                else -> {
                    User(parentContext)

                }
            }
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> "Home"
                1 -> "Game"
                else -> {
                    return "User"
                }
            }
        }
    }
}
