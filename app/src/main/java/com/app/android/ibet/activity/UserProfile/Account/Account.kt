package com.app.android.ibet.activity.UserProfile.Account

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
<<<<<<< HEAD
=======
import android.view.Gravity
>>>>>>> c6b46c1bd3a9e3efaaafb4d3c7b5f379d12f875d
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login.Companion.token
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.MainActivity.Companion.isLogin
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.frag_account.*
import org.json.JSONObject

class Account : Fragment() {
    private var parentContext = context
    companion object {
        var isChange = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_account, container, false)
    }
    override fun onStart() {
        super.onStart()
<<<<<<< HEAD
        val recyclerDataAdapter = RecyclerViewAdapter(getAccDataToPass())
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(parentContext)
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
=======
        if (isChange) {
            val toast = Toast.makeText(
                context,
                "Changes are successfully updated", Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 260)
            val view = toast.view
            view.setBackgroundResource(R.color.toast)
            val text = view.findViewById<TextView>(android.R.id.message)
            text.setTextColor(Color.parseColor("#ffffff"))
            toast.show()
            isChange = false
>>>>>>> c6b46c1bd3a9e3efaaafb4d3c7b5f379d12f875d
        }
        var userData = Api().get(BuildConfig.USER)
        acc_id.text = "ID: " + JSONObject(userData).getString("pk")
        acc_first_name.text = JSONObject(userData).getString("first_name")
        acc_last_name.text = JSONObject(userData).getString("last_name")
        acc_username.text = JSONObject(userData).getString("username")
        acc_email.text = JSONObject(userData).getString("email")
        acc_address.text= JSONObject(userData).getString("street_address_1") + " " +
                JSONObject(userData).getString("city") + " " + JSONObject(userData).getString("country")+
                " " + JSONObject(userData).getString("zipcode")
        acc_phone.text = JSONObject(userData).getString("phone")
        val time = JSONObject(userData).getString("time_of_registration").substring(5,7) + "/" +
                JSONObject(userData).getString("time_of_registration").substring(8,10) + "/" +
                JSONObject(userData).getString("time_of_registration").substring(0,4)
        acc_time.text = time
        acc_edit.setOnClickListener {
            info = "acc_edit"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        logout.setOnClickListener {
            isLogin = false
            token = ""
            startActivity(Intent(activity, MainActivity::class.java))
            activity!!.overridePendingTransition(0, 0)
        }

    }

}