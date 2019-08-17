package com.app.android.ibet.activity.UserProfile.Account

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.frag_account.*

class Account : Fragment() {
    private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_account, container, false)
    }
    override fun onStart() {
        super.onStart()
        val recyclerDataAdapter = RecyclerViewAdapter(getAccDataToPass())
        recyclerView.layoutManager = LinearLayoutManager(parentContext)
        recyclerView.adapter = recyclerDataAdapter
        recyclerView.setHasFixedSize(true)
    }
    private fun getAccDataToPass(): MutableList<ParentDataItem> {
        val accDataItems: MutableList<ParentDataItem> = ArrayList()
        var accChildDataItems: MutableList<ChildDataItem>
        /*
        for (k in 1..4) {
            childDataItems = ArrayList()
            for (i in 0..k) {
                val dummyChildDataItem = ChildDataItem("Child Item $k.$i")
                childDataItems.add(dummyChildDataItem)
            }
            val dummyParentDataItem = ParentDataItem("Parent $k", childDataItems)
            dummyDataItems.add(dummyParentDataItem)
        } */
        //user information
        accChildDataItems = ArrayList()
        for (i in 0..3) {
            val dummyChildDataItem = ChildDataItem("Child Item $1.$i")
            accChildDataItems.add(dummyChildDataItem)
        }
        val infoParentDataItem = ParentDataItem("User Informations:", accChildDataItems)
        accDataItems.add(infoParentDataItem)

        //Security Setting
        accChildDataItems = ArrayList()
        for (i in 0..3) {
            val dummyChildDataItem = ChildDataItem("Child Item $1.$i")
            accChildDataItems.add(dummyChildDataItem)
        }
        val settingDataItem = ParentDataItem("Security Setting:", accChildDataItems)
        accDataItems.add(settingDataItem)

        //support
        accChildDataItems = ArrayList()
        for (i in 0..3) {
            val dummyChildDataItem = ChildDataItem("Child Item $1.$i")
            accChildDataItems.add(dummyChildDataItem)
        }
        val supportDataItem = ParentDataItem("Support:", accChildDataItems)
        accDataItems.add(supportDataItem)

        //inbox
        accChildDataItems = ArrayList()

            val dummyChildDataItem = ChildDataItem("2019/07/06\n\n[Slot Game]\n\nDear customer , ibet lunches new slot games and \n" +
                    "have ¥200 bonus.")
            accChildDataItems.add(dummyChildDataItem)

        val inboxDataItem = ParentDataItem("Inbox:", accChildDataItems)
        accDataItems.add(inboxDataItem)


        return accDataItems
    }
}