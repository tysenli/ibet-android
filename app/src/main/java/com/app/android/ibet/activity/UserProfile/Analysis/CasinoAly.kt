package com.app.android.ibet.activity.UserProfile.Analysis

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.frag_casino_aly.*
import kotlinx.android.synthetic.main.frag_slots_aly.*
import kotlinx.android.synthetic.main.frag_slots_aly.by_day
import kotlinx.android.synthetic.main.frag_slots_aly.by_game

class CasinoAly : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_casino_aly, container, false)
    }

    override fun onStart() {
        super.onStart()
        casino_list_bygame.visibility = View.VISIBLE
        casino_list_byday.visibility = View.GONE
        by_game.setOnClickListener {
            by_game.background = resources.getDrawable(R.color.btn_d)
            by_day.background = resources.getDrawable(R.color.btn_l)
            casino_list_bygame.visibility = View.VISIBLE
            casino_list_byday.visibility = View.GONE

        }
        by_day.setOnClickListener {
            by_game.background = resources.getDrawable(R.color.btn_l)
            by_day.background = resources.getDrawable(R.color.btn_d)
            casino_list_bygame.visibility = View.GONE
            casino_list_byday.visibility = View.VISIBLE
        }


        val game = arrayOf("Game", "Roulette", "Blackjack", "Crazy 8")
        val spins = arrayOf("Spins", "750", "300", "750")
        val rtp = arrayOf("RTP", "95%", "103%", "95%")
        val bet = arrayOf("Bet", "1500", "1500", "400")
        val win = arrayOf("Win", "500", "500", "400")
        val pl = arrayOf("P&L", "500", "-500", "400")

        val day = arrayOf("Day","1 Jul", "2 Jul", "3 Jul")
        val biggest = arrayOf("Biggest Win", "500","300","300")
        val bonus = arrayOf("Bonus", "5","3","3")


        val myListAdapter = SlotsAdapter(activity!!,game,spins,rtp,bet,win,pl)
        casino_list_bygame.adapter = myListAdapter


        val myListDayAdapter = SlotsDayAdapter(activity!!,day,spins,rtp,bet,win,pl,biggest,bonus)
        casino_list_byday.adapter = myListDayAdapter

    }

}