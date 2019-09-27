package com.app.android.ibet.activity.UserProfile.Analysis

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.api.Api
import com.app.android.ibet.api.URLs
import com.wajahatkarim3.easyvalidation.core.view_ktx.atleastOneLowerCase
import kotlinx.android.synthetic.main.frag_sports_aly.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class SportsAly : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_sports_aly, container, false)
    }

    override fun onStart() {
        super.onStart()
        val username = JSONObject(MyAccount.userData).getString("username")

        var placed = arrayOf("Placed")
        var category = arrayOf("Category")
        var winloss = arrayOf("Win/loss")
        var balance = arrayOf("Balance")
        var ID = arrayOf("ID")
        var type = arrayOf("Type")
        var event = arrayOf("Event")
        var line = arrayOf("Line")
        var settled = arrayOf("Settle")
        var result = arrayOf("Result")
        var stake = arrayOf("Stake")

        sports_bets_list.visibility = View.VISIBLE
        sports_detail.visibility = View.GONE

        val request = Request.Builder()
            //.url("http://10.0.2.2:8000/accounting/api/transactions/get_transactions?userid=Jennie")
            .url(URLs.BET_HISTORY + "?username=$username")
            .build()
        val response = OkHttpClient().newCall(request).execute()
        val transaction = response.body()!!.string()
        val records = JSONObject(transaction).getJSONArray("bet")
        Log.e("records", records.toString())

        for (i in 0 until records.length()) {

            placed += records.getJSONObject(i).getString("time")
            category += records.getJSONObject(i).getString("BetType")
            winloss += records.getJSONObject(i).getString("totalWin")
            ID += records.getJSONObject(i).getString("BetID")
            type += records.getJSONObject(i).getString("BetType")
            event += ""
            line += ""
            settled += records.getJSONObject(i).getString("SettleDT")
            result += records.getJSONObject(i).getString("Wintype")
            stake += records.getJSONObject(i).getString("InitBetAmt")
            balance += "0"

        }

        val myListAdapter = SportsAdapter(activity!!,placed,category,winloss,balance)
        sports_bets_list.adapter = myListAdapter

        sports_bets_list.setOnItemClickListener(){adapterView, view, position, id ->

            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            if (position != 0) {
                title.text = "$itemAtPos"
                sports_bets_list.visibility = View.GONE
                sports_detail.visibility = View.VISIBLE
                detail_placed.text = placed[position]
                detail_id.text = ID[position]
                detail_type.text = type[position]
                detail_event.text = event[position]
                detail_line.text = line[position]
                detail_settled.text = settled[position]
                detail_result.text = result[position]
                detail_stake.text = stake[position]
                detail_return.text = winloss[position]

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

