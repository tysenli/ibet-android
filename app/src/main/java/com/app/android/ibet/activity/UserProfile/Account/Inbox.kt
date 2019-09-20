package com.app.android.ibet.activity.UserProfile.Account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.userData
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.BaseSwipeAdapter
import kotlinx.android.synthetic.main.frag_inbox.*
import kotlinx.android.synthetic.main.frag_inbox_detail.*
import okhttp3.MediaType
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

            val myListAdapter = ListViewAdapter(activity!!, userMessageList)
            item.adapter = myListAdapter



            item.setOnItemClickListener() { adapterView, view, position, id ->
                pos = position
                MyAccount.info = "inbox_detail"
                startActivity(Intent(activity, MyAccount::class.java))
                activity!!.overridePendingTransition(0, 0)
                // true


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
/*
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
} */

class ListViewAdapter(private val mContext: Context,private val userMessageList: JSONArray) : BaseSwipeAdapter() {

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }

    override fun generateView(position: Int, parent: ViewGroup): View {
        val v = LayoutInflater.from(mContext).inflate(R.layout.frag_inbox_item, null)
        val swipeLayout = v.findViewById(getSwipeLayoutResourceId(position)) as SwipeLayout


        if (userMessageList.getJSONObject(position).getBoolean("is_read")) {
            val bg = v.findViewById(R.id.unread) as LinearLayout
            bg.setBackgroundResource(R.drawable.read)
        }

        swipeLayout.setOnDoubleClickListener { layout, surface ->
            Toast.makeText(
                mContext,
                "DoubleClick",
                Toast.LENGTH_SHORT
            ).show()
        }
        v.findViewById<TextView>(R.id.delete).setOnClickListener(View.OnClickListener {
            // Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show()
            val client = OkHttpClient()
            val pk = userMessageList.getJSONObject(Inbox.pos).getString("pk")
            val JSON = MediaType.get("application/json; charset=utf-8")

            val req = Request.Builder()
                .url(BuildConfig.USER_INBOX_DELETE + pk)
                .post(RequestBody.create(JSON, JSONObject().toString()))
                .build()
            val response = client.newCall(req).execute()
            if (response.code() == 200) {
                MyAccount.info = "inbox"
                mContext.startActivity(Intent(mContext, MyAccount::class.java))
                // ((Activity)mContext).overridePendingTransition(0, 0)
            }
        })
        return v
    }

    override fun fillValues(position: Int, convertView: View) {


        val subjectText = convertView.findViewById<View>(R.id.inbox_subject) as TextView
        subjectText.text = userMessageList.getJSONObject(position).getString("subject")

        val contentText = convertView.findViewById<View>(R.id.inbox_content) as TextView
        contentText.text = userMessageList.getJSONObject(position).getString("content")

        val timeText  = convertView.findViewById<View>(R.id.inbox_time) as TextView
        timeText .text = userMessageList.getJSONObject(position).getString("publish_on")
    }

    override fun getCount(): Int {
        return userMessageList.length()
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}