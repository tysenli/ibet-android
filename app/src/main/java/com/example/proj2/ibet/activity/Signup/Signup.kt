package com.example.proj2.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.proj2.ibet.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_email_auth_p2.*
import kotlinx.android.synthetic.main.activity_signup.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

import java.util.*


class Signup : AppCompatActivity() {

    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_signup)
        if (android.os.Build.VERSION.SDK_INT > 9) {
            var policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

        }

        //var btnLoginFacebook = findViewById<Button>(R.id.btnLoginFacebook)

        btnLoginFacebook.setOnClickListener {
            // Signup
            FacebookSdk.sdkInitialize(this.applicationContext)
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        Log.d("MainActivity", "Facebook token: " + loginResult.accessToken.token)
                        startActivity(Intent(applicationContext, fbAuthenticatedActivity::class.java))
                    }

                    override fun onCancel() {
                        Log.d("MainActivity", "Facebook onCancel.")

                    }

                    override fun onError(error: FacebookException) {
                        Log.d("MainActivity", "Facebook onError.")

                    }
                })
        }

        btnLoginEmail.setOnClickListener {
            startActivity(Intent(applicationContext, emailAuthP1::class.java))
        }
        btnOneClick.setOnClickListener {
            val visitorJson = JSONObject()
            //println("this is birth: " + birth_show.text.toString())
            var user = "visitor" + (100..999).random().toString()
            val STRING_CHARACTERS = ('0'..'z').toList().toTypedArray()
            val password = (8..16).map { STRING_CHARACTERS.random() }.joinToString("")

           // println("password:1111" + password)

            visitorJson.put("username"         , user)
            visitorJson.put("password1"        , password)
            visitorJson.put("password2"        , password)
            visitorJson.put("email"            , "test@gmail.com")
            visitorJson.put("phone"            , "3142003333")
            visitorJson.put("first_name"       , "test")
            visitorJson.put("last_name"        , "test")

            visitorJson.put("gender"           , "male")
            visitorJson.put("date_of_birth"    , "01/01/1990")
            visitorJson.put("country"          , "USA")

            visitorJson.put("city"             , "test")
            visitorJson.put("state"            , "MO")
            visitorJson.put("zipcode"          , "63130")

            val url = "http://10.0.2.2:8000/users/api/signup/"

            emailAuthP2().post(visitorJson.toString(), url)
            var res = Intent(applicationContext, oneClick::class.java)
            res.putExtra("username",user)
            res.putExtra("password", password)
            startActivity(res)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}
