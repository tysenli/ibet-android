package com.app.android.ibet.activity.UserProfile.Account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.frag_inbox.*
import javax.security.auth.Subject

class Inbox : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_inbox, container, false)
    }

    override fun onStart() {
        super.onStart()

        var subject = arrayOf("Sub", "Subject", "Hi")
        var content = arrayOf("Content", "Con", "Hello")
        var date = arrayOf("07/31", "07/31", "07/31")
        val myListAdapter = InboxAdapter(activity!!, subject, content, date)
        item.adapter = myListAdapter
        //println(records.getJSONObject(1).getString(“method”))
        //println(records.length())
        /*
        var date = arrayOf(“Date”)
        var time = arrayOf(“Time”)
        var category = arrayOf(“Category”)
        var amount = arrayOf(“Amount”)
        var balance = arrayOf(“Balance”)
        var ID = arrayOf(“ID”)
        var type = arrayOf(“Type”)
        var real_cnt = -1

        for (i in 0 until records.length()) {

            if (records.getJSONObject(i).getString(“status”) == “0” ) {
                real_cnt++
                date += records.getJSONObject(i).getString(“request_time”).split(“T”)[0]
                time += records.getJSONObject(i).getString(“request_time”).split(“T”)[1]
                when (records.getJSONObject(i).getString(“transaction_type”) == “0”) {
                true -> category += “Deposit”
                false -> category += “Withdrawal”
            }
                amount += records.getJSONObject(i).getString(“amount”)
                ID += records.getJSONObject(i).getString(“order_id”)
                type += records.getJSONObject(i).getString(“method”)
                if (real_cnt == 0) {
                    balance += amt
                } else if (category[real_cnt] == “Deposit”) {
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
                trans_title.text = “$itemAtPos”
                depowith_list.visibility = View.GONE
                transaction_detail.visibility = View.VISIBLE
                detail_date.text = date[position]
                detail_id.text = ID[position]
                detail_type.text = type[position]
                detail_amount.text = amount[position]
                detail_bnc.text = balance[position]
                //Toast.makeText(context, “Click on item at $itemAtPos its item id $itemIdAtPos”, Toast.LENGTH_LONG).show()
            }
        }
        trans_title.setOnClickListener {
            trans_title.text = “Deposit & Withdraw”
            depowith_list.visibility = View.VISIBLE
            transaction_detail.visibility = View.GONE
        }
    }
} */
    }
}
class InboxAdapter(private val context: FragmentActivity, private val subject: Array<String>, private val content: Array<String>, private val time: Array<String>)
    : ArrayAdapter<String>(context, R.layout.frag_inbox_item, subject) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.frag_inbox_item, null, true)

        val subjectText = rowView.findViewById(R.id.inbox_subject) as TextView
        val contentText = rowView.findViewById(R.id.inbox_content) as TextView
        val timeText = rowView.findViewById(R.id.time) as TextView



        subjectText.text = subject[position]
        contentText.text = content[position]
        timeText.text = time[position]


        return rowView
    }
}
