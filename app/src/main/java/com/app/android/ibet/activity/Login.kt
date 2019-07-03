package com.app.android.ibet.activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity.Companion.isLogin
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.Signup.emailAuthP3

import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class Login : AppCompatActivity() {
    companion object {
        var token = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)

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
        userlogin.setOnClickListener {

            val loginJson = JSONObject()
            loginJson.put("username", login_user.text.toString())
            //loginJson.put("email", login_email.text.toString())
            loginJson.put("password", login_password.text.toString())

            //val url = "http://10.0.2.2:8000/users/api/login/"

            var log = Signup().post(loginJson.toString(), BuildConfig.LOGIN)
            println (log)
            var key = log.split(":")[1]
            // println(key.substring(1,key.length - 2))
            token = key.substring(1,key.length - 2)
            println(token)
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