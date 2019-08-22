package com.app.android.ibet.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.adapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.fragment_casino.*
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem




@SuppressLint("ValidFragment")
class Casino (context: Context): Fragment() {

    private var parentContext = context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_casino, container, false)
    }
    override fun onStart() {
        super.onStart()

        val adapter = FragmentPagerItemAdapter(
            fragmentManager, FragmentPagerItems.with(parentContext)
                .add("ALL", GameLobbyAll().javaClass)
                .add("ROULETTE", GameLobbyAll().javaClass)
                .add("BLACKJACK", GameLobbyAll().javaClass)
                .add("BACCARAT", GameLobbyAll().javaClass)
                .add("POKER", GameLobbyAll().javaClass)
                .add("TOURNAMENTS", GameLobbyAll().javaClass)
                .create()
        )
        casino_viewpager.adapter = adapter
        casino_viewpagertab.setViewPager(casino_viewpager)


    }


}