package com.example.proj2.ibet.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.proj2.ibet.R
import com.example.proj2.ibet.activity.LostDetailsSet.LostDetails
import com.example.proj2.ibet.activity.Signup.Signup
import kotlinx.android.synthetic.*

import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


@SuppressLint("ValidFragment")
class Login (context: Context): Fragment() {

    private var parentContext = context
    private var nameField: EditText? = null
    private var passwordField: EditText? = null
    private var loginDir: Button? = null
    private var failedInfo: TextView? = null
    private var counter = 10


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        /*
        Fuel.get("http://10.0.2.2:8000/users/api/games/?term=Casino")
            .response{request, response, result ->
                //println(request)
                println(response)

            }*/


        login_button.setOnClickListener {
            //Log.d("btnSetup", "Selected")
            val visitorJson = JSONObject()
            val info = post(visitorJson.toString(), BuilderConfig.LOGIN_URL)
            val info1= info.split
            login_button.autoSizeMaxTextSize
            var intent = Intent(parentContext, Signup::class.java)
            startActivity(intent)
        }

        forgot_details.setOnClickListener {view ->
            forgot_details.autoSizeMaxTextSize
            var intent = Intent(parentContext, LostDetails::class.java)
            startActivity(intent)
        }
        /*
        usersignup.setOnClickListener { view ->
            //Log.d("btnSetup", "Selected")
            usersignup.setBackgroundColor(Color.LTGRAY)
            var intent = Intent(parentContext, Signup::class.java)
            startActivity(intent)
        }*/
    }

    fun post(json: String, url: String): String{
        val client = OkHttpClient()

        val JSON = MediaType.get("application/json; charset=utf-8")
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()

            .url(url)
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        println(response.request())

        return response.body()!!.string()

    }

}