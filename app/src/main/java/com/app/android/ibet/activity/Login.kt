package com.app.android.ibet.activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity.Companion.isLogin
import com.app.android.ibet.activity.Signup.Signup

import kotlinx.android.synthetic.main.activity_login.*



class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_login)
        forgot_password.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        sign_up_here.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        /*
        Fuel.get("http://10.0.2.2:8000/users/api/games/?term=Casino")
            .response{request, response, result ->
                //println(request)
                println(response)

            }*/
        sign_up_here.setOnClickListener { view ->
            //Log.d("btnSetup", "Selected")
            //sign_up_here.autoSizeMaxTextSize
            var intent = Intent(this, Signup::class.java)
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
            startActivity(Intent(this, MainActivity::class.java))

        }
        forgot_password.setOnClickListener {
            startActivity(Intent(this, ForgotPass::class.java))

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