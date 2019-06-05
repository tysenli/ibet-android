package com.app.android.ibet.fragment

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
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.MainActivity.Companion.isLogin
import com.app.android.ibet.activity.Signup.Signup

import kotlinx.android.synthetic.main.fragment_login.*


@SuppressLint("ValidFragment")
class Login (context: Context): Fragment() {

    private var parentContext = context


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
        sign_up_here.setOnClickListener { view ->
            //Log.d("btnSetup", "Selected")
            sign_up_here.autoSizeMaxTextSize
            var intent = Intent(parentContext, Signup::class.java)
            startActivity(intent)
        }
        userlogin.setOnClickListener { view ->
            /*
            val loginJson = JSONObject()
            loginJson.put("username", "Jennie")
            loginJson.put("email", "jiaqi.hu17@gmail.com")
            loginJson.put("password", "lub13080")

            val url = "http://10.0.2.2:8000/users/api/login/"

            emailAuthP2().post(loginJson.toString(), url)
            */

            isLogin = true
            startActivity(Intent(parentContext, MainActivity::class.java))

        }
        /*
        usersignup.setOnClickListener { view ->
            //Log.d("btnSetup", "Selected")
            usersignup.setBackgroundColor(Color.LTGRAY)
            var intent = Intent(parentContext, Signup::class.java)
            startActivity(intent)
        }*/
    }



}