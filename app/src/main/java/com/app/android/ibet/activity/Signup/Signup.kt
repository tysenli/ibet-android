package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle

import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.api.Api
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.github.kittinunf.fuel.core.Body
import kotlinx.android.synthetic.main.activity_signup.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

import java.util.*


class Signup : AppCompatActivity() {

    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
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
            val info = Api().post(visitorJson.toString(),BuildConfig.ONE_CLICK_SIGNUP_URL)
            println (info)
            var info1 = info!!.split(",")
            var info2 = info1[0].split(":")[1]
            var info3 = info1[1].split(":")[1]
            var name = info2.substring(1,info2.length - 1)
            var pass = info3.substring(1, info3.length - 2)
           // println(name)

            var res = Intent(applicationContext, oneClick::class.java)
            res.putExtra("username",name)
            res.putExtra("password",pass)

            startActivity(res)

        }



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
    /*
    fun post(json : String, url : String):  String {

        val client = OkHttpClient()

        val JSON = MediaType.get("application/json; charset=utf-8")
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
            // .addHeader("Authorization", "Bearer $token")
            .url(url)
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        println(response.request())
        //println("this is:" + response.body()!!.string())
        return response.body()!!.string()


    } */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}
