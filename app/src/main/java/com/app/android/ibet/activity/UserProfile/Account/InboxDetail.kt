package com.app.android.ibet.activity.UserProfile.Account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.Account.Inbox.Companion.isDelete
import com.app.android.ibet.activity.UserProfile.Account.Inbox.Companion.pos
import com.app.android.ibet.activity.UserProfile.MyAccount
import kotlinx.android.synthetic.main.frag_inbox_detail.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

class InboxDetail: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_inbox_detail, container, false)
    }

    override fun onStart() {
        super.onStart()

        val client = OkHttpClient()
//        val JSON = MediaType.get("application/json; charset=utf-8")
//        val body = RequestBody.create(JSON, changeJson.toString())
        val request = Request.Builder()
//            .addHeader("Authorization", "token " + Login.token)
            .url(BuildConfig.USER_INBOX + JSONObject(MyAccount.userData).getString("pk"))
//            .post(body)
            .build()

        val response = client.newCall(request).execute()
        if (response.code() == 400) {
        }
        else {
            val messageList = response.body()!!.string()
            //println(messageList)
            val userMessageList = JSONArray(messageList)
            val pk = userMessageList.getJSONObject(pos).getString("pk")
            val JSON = MediaType.get("application/json; charset=utf-8")
            val request = Request.Builder()
//            .addHeader("Authorization", "token " + Login.token)
                .url(BuildConfig.USER_INBOX_READ + pk)
                .post(RequestBody.create(JSON, JSONObject().toString()))
                .build()
            val response = client.newCall(request).execute()
            if (response.code() == 400) {
                println(response.body()!!.string())
            }

            inbox_subject.text = userMessageList.getJSONObject(pos).getString("subject")
            inbox_content_text.text = userMessageList.getJSONObject(pos).getString("content")
            inbox_time.text = userMessageList.getJSONObject(pos).getString("publish_on")

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

                    isDelete = true
                }
            }
        }

    }

}