package com.app.android.ibet.activity.UserProfile.Analysis

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.app.android.ibet.R
import com.wajahatkarim3.easyvalidation.core.view_ktx.atleastOneLowerCase
import kotlinx.android.synthetic.main.frag_depowith_aly.*
import kotlinx.android.synthetic.main.frag_sports_aly.*

class DepoWithAly : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_depowith_aly, container, false)
    }

    override fun onStart() {
        super.onStart()
        val date = arrayOf("Date", "15 Jul", "15 Jul", "15 Jul")
        val time = arrayOf("Time", "11:00", "13:00", "15:00")
        val category = arrayOf("Category","Withdraw", "Deposit", "Deposit")
        val amount = arrayOf("Amount","200","100","100")
        val balance = arrayOf("Balance", "500", "500", "400")
        val myListAdapter = DepoWithAdapter(activity!!,date,time,category,amount,balance)
        depowith_list.adapter = myListAdapter

        depowith_list.setOnItemClickListener() { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            if (position != 0) {
                trans_title.text = "$itemAtPos"
                depowith_list.visibility = View.GONE
                transaction_detail.visibility = View.VISIBLE
                //Toast.makeText(context, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
            }
        }
        trans_title.setOnClickListener {
            trans_title.text = "Deposit & Withdraw"
            depowith_list.visibility = View.VISIBLE
            transaction_detail.visibility = View.GONE
        }
    }
}

class DepoWithAdapter(private val context: FragmentActivity, private val date: Array<String>, private val time: Array<String>, private val category: Array<String>, private val amount: Array<String>, private val balance: Array<String>)
    : ArrayAdapter<String>(context, R.layout.frag_depowith_item, category) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.frag_depowith_item, null, true)

        val dateText = rowView.findViewById(R.id.date) as TextView
        val timeText = rowView.findViewById(R.id.time) as TextView
        val categoryText = rowView.findViewById(R.id.category_depowith) as TextView
        val amountText = rowView.findViewById(R.id.amount) as TextView
        val balanceText = rowView.findViewById(R.id.bnc_depowith) as TextView

        if(date[position] != "Date") {
            categoryText.setTextColor(Color.rgb(0,145,255))
        }

        dateText.text = date[position]
        timeText.text = time[position]
        categoryText.text = category[position]
        amountText.text = amount[position]
        balanceText.text = balance[position]

        return rowView
    }
}