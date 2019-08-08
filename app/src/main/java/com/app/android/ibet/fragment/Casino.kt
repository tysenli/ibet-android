package com.app.android.ibet.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.fragment_casino.*

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
                .add("ROULETTE", GameLobbyRoulette().javaClass)
                .add("BLACKJACK", GameLobbyBlackjack().javaClass)
                .add("BACCARAT", GameLobbyBaccarat().javaClass)
                .add("POKER", GameLobbyPoker().javaClass)
                .add("TOURNAMENTS", GameLobbyTournaments().javaClass)
                .create()
        )
        casino_viewpager.adapter = adapter
        casino_viewpagertab.setViewPager(casino_viewpager)
    }

}