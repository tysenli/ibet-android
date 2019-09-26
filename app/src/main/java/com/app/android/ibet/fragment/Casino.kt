package com.app.android.ibet.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import com.app.android.ibet.fragment.AllGames.AllGames
import com.app.android.ibet.fragment.NewGames.NewGames
import com.app.android.ibet.fragment.NewGames.PopularGames
import com.app.android.ibet.fragment.NewGames.SlotsGames
import com.app.android.ibet.fragment.NewGames.TableGames
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

                .add("All Games", AllGames().javaClass)
                .add("New", NewGames().javaClass)
                .add("Popular", PopularGames().javaClass)
                .add("Table Games", TableGames().javaClass)
                .add("Jackpot", Live().javaClass)
                .add("Slots", SlotsGames().javaClass)
                .add("Promo", Live().javaClass)
                .create()
        )
        casino_viewpager.adapter = adapter
        casino_viewpagertab.setViewPager(casino_viewpager)



    }

}