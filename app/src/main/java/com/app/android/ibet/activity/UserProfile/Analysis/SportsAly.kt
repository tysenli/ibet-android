package com.app.android.ibet.activity.UserProfile.Analysis

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.app.android.ibet.R
import com.wajahatkarim3.easyvalidation.core.view_ktx.atleastOneLowerCase
import kotlinx.android.synthetic.main.frag_sports_aly.*

class SportsAly : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_sports_aly, container, false)
    }

    override fun onStart() {
        super.onStart()
        val placed = arrayOf("Placed", "15 Jul", "13:00", "13:10")
        val category = arrayOf("Category", "Sports Bets", "Single NBA Kinick vs Celtics", "Single CL Man U vs Chelsea")
        val winloss = arrayOf("Win/loss", "200", "100", "100")
        val balance = arrayOf("Balance", "500", "500", "400")
        val myListAdapter = SportsAdapter(activity!!,placed,category,winloss,balance)
        sports_bets_list.visibility = View.VISIBLE
        sports_detail.visibility = View.GONE
        sports_bets_list.adapter = myListAdapter

        sports_bets_list.setOnItemClickListener(){adapterView, view, position, id ->

            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            if (position != 0) {
                title.text = "$itemAtPos"
                sports_bets_list.visibility = View.GONE
                sports_detail.visibility = View.VISIBLE
                //Toast.makeText(context, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
            }
        }
        title.setOnClickListener {
            title.text = "Sports Bets - Jul 2019"
            sports_bets_list.visibility = View.VISIBLE
            sports_detail.visibility = View.GONE
        }

    }
}

class SportsAdapter(private val context: FragmentActivity, private val placed: Array<String>, private val category: Array<String>, private val winloss: Array<String>, private val balance: Array<String>)
    : ArrayAdapter<String>(context, R.layout.frag_sports_item, category) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.frag_sports_item, null, true)

        val placeText = rowView.findViewById(R.id.placed) as TextView
        val categoryText = rowView.findViewById(R.id.category) as TextView
        val winlossText = rowView.findViewById(R.id.win_loss) as TextView
        val balanceText = rowView.findViewById(R.id.bnc) as TextView

        if(!placed[position].atleastOneLowerCase()) {
            categoryText.setTextColor(Color.rgb(0,145,255))
        }

        placeText.text = placed[position]
        categoryText.text = category[position]
        winlossText.text = winloss[position]
        balanceText.text = balance[position]


        return rowView
    }
}

