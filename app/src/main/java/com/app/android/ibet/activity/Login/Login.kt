package com.app.android.ibet.activity.Login

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.MainActivity.Companion.isLogin
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.amt
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.userData
import com.app.android.ibet.api.Api
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty

import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class Login : AppCompatActivity() {
    companion object {
        var token = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
        userlogin.isEnabled = false
        forgot_password.paintFlags = Paint.UNDERLINE_TEXT_FLAG


        login_password.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (login_user.nonEmpty() && login_password.nonEmpty()) {
                    userlogin.setBackgroundResource(R.drawable.btn_red)
                    userlogin.isEnabled = true
                } else {
                    userlogin.setBackgroundResource(R.drawable.btn_red2)
                    userlogin.isEnabled = false
                }

            }

        })
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

            var log = Api().post(loginJson.toString(), BuildConfig.LOGIN)

            if (log.toString().equals("null")) {
                forgot_password.text = "Incorrect Username or Password\n Forgot Password?"
                forgot_password.setTextColor(Color.RED)
            } else {
               // var hint = log!!.split(":")[0]
                //var key = log!!.split(":")[1]
                // println(key.substring(1,key.length - 2))
                //var success = hint.substring(2, hint.length - 1)
                token = JSONObject(log).getString("key")
                isLogin = true
                userData = Api().get(BuildConfig.USER)!!
                amt = JSONObject(userData).getString("main_wallet")
                startActivity(Intent(this, MainActivity::class.java))
            }


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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            android.R.id.home -> {
                // startActivity(Intent(this, MyAccount::class.java))
                onBackPressed()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }


}