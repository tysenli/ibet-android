package com.app.android.ibet.activity.UserProfile.ResponsibleGame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.emailAuthP1
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.ResponsibleGame.ResponsibleGame.Companion.lockInterval
import kotlinx.android.synthetic.main.frag_lock.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class Lock : Fragment() {
    //private var parentContext = context
    //var userData = Api().get(BuildConfig.USER)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_lock, container, false)
    }

    override fun onStart() {
        super.onStart()
        done_lock.setOnClickListener {
            val client = OkHttpClient()
            val lockJson = JSONObject()

            val JSON = MediaType.get("application/json; charset=utf-8")
            lockJson.put("timespan", lockInterval)

            lockJson.put("userId", JSONObject(MyAccount.userData).getString("pk"))
            val body = RequestBody.create(JSON,  lockJson.toString())
            val request = Request.Builder()
                .url(BuildConfig.LOCKTIME)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            MainActivity.isLogin = false
            Login.token = ""
            startActivity(Intent(activity, MainActivity::class.java))
            activity!!.overridePendingTransition(0, 0)

        }
        /*
        revert.setOnClickListener {
            MyAccount.info = "rg"
            var res = Intent(activity, MyAccount::class.java)
            startActivity(res)
            activity!!.overridePendingTransition(0, 0)
        } */
    }
}