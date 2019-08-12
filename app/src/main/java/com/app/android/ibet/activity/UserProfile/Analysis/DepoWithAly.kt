package com.app.android.ibet.activity.UserProfile.Analysis

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.amt
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.userData
import com.app.android.ibet.api.Api
import com.wajahatkarim3.easyvalidation.core.view_ktx.atleastOneLowerCase
import kotlinx.android.synthetic.main.frag_depowith_aly.*
import kotlinx.android.synthetic.main.frag_sports_aly.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class DepoWithAly : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_depowith_aly, container, false)
    }

    override fun onStart() {
        super.onStart()
        val username = JSONObject(userData).getString("username")
        depowith_list.visibility = View.VISIBLE
        transaction_detail.visibility = View.GONE

        val request = Request.Builder()
            //.url("http://10.0.2.2:8000/accounting/api/transactions/get_transactions?userid=Jennie")
            .url(BuildConfig.TRANSACTION_RECODE + username)
            .build()
        val response = OkHttpClient().newCall(request).execute()
        val transaction = response.body()!!.string()
        val records = JSONObject(transaction).getJSONArray("results")
        //println(records.getJSONObject(1).getString("method"))
        //println(records.length())
        var date = arrayOf("Date")
        var time = arrayOf("Time")
        var category = arrayOf("Category")
        var amount = arrayOf("Amount")
        var balance = arrayOf("Balance")
        var ID = arrayOf("ID")
        var type = arrayOf("Type")
        var real_cnt = -1

        for (i in 0 until records.length()) {

            if (records.getJSONObject(i).getString("transaction_id") != "0" && records.getJSONObject(i).getString("transaction_id")[0].isDigit()) {
                real_cnt++
                date += records.getJSONObject(i).getString("request_time").split("T")[0]
                time += records.getJSONObject(i).getString("request_time").split("T")[1]
                when (records.getJSONObject(i).getString("transaction_type") == "0") {
                    true -> category += "Deposit"
                    false -> category += "Withdrawal"
                }
                amount += records.getJSONObject(i).getString("amount")
                ID += records.getJSONObject(i).getString("order_id")
                type += records.getJSONObject(i).getString("method")
                if (real_cnt == 0) {
                    balance += amt
                } else if (category[real_cnt] == "Deposit") {
                    balance += (balance[real_cnt].toFloat() - amount[real_cnt].toFloat()).toString()
                } else {
                    balance += (balance[real_cnt].toFloat() + amount[real_cnt].toFloat()).toString()
                }
            }
        }


        val myListAdapter = DepoWithAdapter(activity!!,date,time,category,amount,balance)
        depowith_list.adapter = myListAdapter

        depowith_list.setOnItemClickListener() { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            if (position != 0) {
                trans_title.text = "$itemAtPos"
                depowith_list.visibility = View.GONE
                transaction_detail.visibility = View.VISIBLE
                detail_date.text = date[position]
                detail_id.text = ID[position]
                detail_type.text = type[position]
                detail_amount.text = amount[position]
                detail_bnc.text = balance[position]
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