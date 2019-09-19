package com.app.android.ibet.activity.UserProfile.Account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.userData
import kotlinx.android.synthetic.main.frag_inbox.*
import kotlinx.android.synthetic.main.frag_inbox_detail.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.security.auth.Subject

class Inbox : Fragment() {
    //private var parentContext = context
    companion object {
        var pos = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_inbox, container, false)
    }

    override fun onStart() {
        super.onStart()

        val client = OkHttpClient()
//        val JSON = MediaType.get("application/json; charset=utf-8")
//        val body = RequestBody.create(JSON, changeJson.toString())
        val request = Request.Builder()
//            .addHeader("Authorization", "token " + Login.token)
            .url(BuildConfig.USER_INBOX + JSONObject(userData).getString("pk"))
//            .post(body)
            .build()

        val response = client.newCall(request).execute()
        if (response.code() == 400) {
        }
        else {
            val messageList = response.body()!!.string()
            val userMessageList = JSONArray(messageList)

            var pk = arrayOf<String>()


            for (i in 0 until userMessageList.length()) {
                pk += userMessageList.getJSONObject(i).getString("pk")
            }

            val myListAdapter = InboxAdapter(activity!!, userMessageList, pk)
            item.adapter = myListAdapter
            item.setOnItemClickListener() {adapterView, view, position, id ->
                pos = position
                MyAccount.info = "inbox_detail"
                startActivity(Intent(activity, MyAccount::class.java))
                activity!!.overridePendingTransition(0, 0)
            }
/*
            delete.setOnClickListener {
                val req = Request.Builder()
                    .url(BuildConfig.USER_INBOX_DELETE + pk)
                    .post(RequestBody.create(JSON, JSONObject().toString()))
                    .build()
                val response = client.newCall(req).execute()
                if (response.code() == 200) {
                    MyAccount.info = "inbox"
                    startActivity(Intent(activity, MyAccount::class.java))
                    activity!!.overridePendingTransition(0, 0)
            }*/
        }


    }
}
// private val userMessageList: JSONArray
class InboxAdapter(private val context: FragmentActivity, private val userMessageList: JSONArray ,private val pk: Array<String>):
    ArrayAdapter<String>(context, R.layout.frag_inbox_item, pk) {


    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.frag_inbox_item, null, true)


        if (userMessageList.getJSONObject(position).getBoolean("is_read")) {
            val bg = rowView.findViewById(R.id.unread) as LinearLayout
            bg.setBackgroundResource(R.drawable.read)
        }

        val subjectText = rowView.findViewById(R.id.inbox_subject) as TextView
        val contentText = rowView.findViewById(R.id.inbox_content) as TextView
        val timeText = rowView.findViewById(R.id.inbox_time) as TextView



        subjectText.text = userMessageList.getJSONObject(position).getString("subject")
        contentText.text =userMessageList.getJSONObject(position).getString("content")
        timeText.text = userMessageList.getJSONObject(position).getString("publish_on")


        return rowView
    }
}
