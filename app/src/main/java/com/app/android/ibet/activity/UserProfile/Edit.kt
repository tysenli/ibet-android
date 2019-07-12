package com.app.android.ibet.activity.UserProfile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_change_pass.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class Edit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_profile)
        /*
        var editJson = JSONObject()
//        editJson.put("current_password", cur_pass.text.toString())
        val client = OkHttpClient()

        val JSON = MediaType.get("application/json; charset=utf-8")
        val body = RequestBody.create(JSON, editJson.toString())

        val request = Request.Builder()
            // .addHeader("Authorization", "Bearer $token")
            .url("")
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        println(response.request())
        //println("this is:" + response.body()!!.string())
        // response.body()!!.string()
        */
    }
}