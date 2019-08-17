package com.app.android.ibet.activity.UserProfile.Analysis

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.app.android.ibet.R
import com.wajahatkarim3.easyvalidation.core.view_ktx.atleastOneLowerCase
import com.wajahatkarim3.easyvalidation.core.view_ktx.startsWith
import kotlinx.android.synthetic.main.frag_analysis.*
import kotlinx.android.synthetic.main.frag_depowith_aly.*
import kotlinx.android.synthetic.main.frag_slots_aly.*
import kotlinx.android.synthetic.main.frag_sports_aly.*

class SlotsAly : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_slots_aly, container, false)
    }

    override fun onStart() {
        super.onStart()
        slots_list_bygame.visibility = View.VISIBLE
        slots_list_byday.visibility = View.GONE
        by_game.setOnClickListener {
            by_game.background = resources.getDrawable(R.color.btn_d)
            by_day.background = resources.getDrawable(R.color.btn_l)
            slots_list_bygame.visibility = View.VISIBLE
            slots_list_byday.visibility = View.GONE

        }
        by_day.setOnClickListener {
            by_game.background = resources.getDrawable(R.color.btn_l)
            by_day.background = resources.getDrawable(R.color.btn_d)
            slots_list_bygame.visibility = View.GONE
            slots_list_byday.visibility = View.VISIBLE
        }


        val game = arrayOf("Game", "Book pf Dead", "Starburst", "Dooms")
        val spins = arrayOf("Spins", "750", "300", "750")
        val rtp = arrayOf("RTP", "95%", "103%", "95%")
        val bet = arrayOf("Bet", "1500", "1500", "400")
        val win = arrayOf("Win", "500", "500", "400")
        val pl = arrayOf("P&L", "500", "-500", "400")

        val day = arrayOf("Day","1 Jul", "2 Jul", "3 Jul")
        val biggest = arrayOf("Biggest Win", "500","300","300")
        val bonus = arrayOf("Bonus", "5","3","3")


        val myListAdapter = SlotsAdapter(activity!!,game,spins,rtp,bet,win,pl)
        slots_list_bygame.adapter = myListAdapter

        slots_list_bygame.setOnItemClickListener(){adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(context, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
        }

        val myListDayAdapter = SlotsDayAdapter(activity!!,day,spins,rtp,bet,win,pl,biggest,bonus)
        slots_list_byday.adapter = myListDayAdapter





    }

}

class SlotsAdapter(private val context: FragmentActivity, private val game: Array<String>, private val spins: Array<String>, private val rtp: Array<String>, private val bet: Array<String>, private val win: Array<String>, private val pl: Array<String>)
    : ArrayAdapter<String>(context, R.layout.frag_slot_bygame_item, game) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.frag_slot_bygame_item, null, true)

        val gameText = rowView.findViewById(R.id.game) as TextView
        val spinsText = rowView.findViewById(R.id.spins) as TextView
        val rtpText = rowView.findViewById(R.id.rtp) as TextView
        val betText = rowView.findViewById(R.id.bet) as TextView
        val winText = rowView.findViewById(R.id.win) as TextView
        val plText = rowView.findViewById(R.id.pl) as TextView


        if(pl[position].startsWith("-")) {
            plText.setTextColor(Color.rgb(0,255,0))
        } else if (pl[position].startsWith("P")) {
            plText.setTextColor(Color.BLACK)
        } else {
            plText.setTextColor(Color.rgb(255,0,0))
        }

        gameText.text = game[position]
        spinsText.text = spins[position]
        rtpText.text = rtp[position]
        betText.text = bet[position]
        winText.text = win[position]
        plText.text = pl[position]

        return rowView
    }
}

class SlotsDayAdapter(private val context: FragmentActivity, private val day: Array<String>, private val spins: Array<String>, private val rtp: Array<String>, private val bet: Array<String>, private val win: Array<String>,
                      private val pl: Array<String>, private val biggest: Array<String>, private val bonus: Array<String>)
    : ArrayAdapter<String>(context, R.layout.frag_slot_byday_item, day) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.frag_slot_byday_item, null, true)

        val dayText = rowView.findViewById(R.id.day) as TextView
        val spinsText = rowView.findViewById(R.id.spins_day) as TextView
        val rtpText = rowView.findViewById(R.id.rtp_day) as TextView
        val betText = rowView.findViewById(R.id.bet_day) as TextView
        val winText = rowView.findViewById(R.id.win_day) as TextView
        val plText = rowView.findViewById(R.id.pl_day) as TextView
        val biggestText = rowView.findViewById(R.id.biggest_win) as TextView
        val bonusText = rowView.findViewById(R.id.bonus) as TextView


        if(pl[position].startsWith("-")) {
            plText.setTextColor(Color.rgb(0,255,0))
        } else if (pl[position].startsWith("P")) {
            plText.setTextColor(Color.BLACK)
        } else {
            plText.setTextColor(Color.rgb(255,0,0))
        }

        dayText.text = day[position]
        spinsText.text = spins[position]
        rtpText.text = rtp[position]
        betText.text = bet[position]
        winText.text = win[position]
        plText.text = pl[position]
        biggestText.text = biggest[position]
        bonusText.text = bonus[position]

        return rowView
    }
}
