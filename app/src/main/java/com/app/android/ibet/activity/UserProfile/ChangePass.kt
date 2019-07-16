package com.app.android.ibet.activity.UserProfile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import kotlinx.android.synthetic.main.activity_change_pass.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class ChangePass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)

        change_pass.setOnClickListener {

            var correctNewPass = false
            var notsame = false
            if (cur_pass.text.toString().equals(new_pass.text.toString())) {
                new_pass_error.text = "new password cannot be the same as the current one."
                new_pass_error.setTextColor(Color.RED)
            } else {
                new_pass_error.text = ""
                notsame = true
            }
            if (!new_pass.text.toString().equals(confirm_pass.text.toString())) {
                new_pass_error.text = "two passwords are not the same."
                new_pass_error.setTextColor(Color.RED)
            } else {
                new_pass_error.text = ""
                correctNewPass = true
            }

            if (notsame && correctNewPass) {

                var changeJson = JSONObject()
                changeJson.put("current_password", cur_pass.text.toString())
                changeJson.put("new_password", new_pass.text.toString())

                val client = OkHttpClient()

                val JSON = MediaType.get("application/json; charset=utf-8")
                val body = RequestBody.create(JSON, changeJson.toString())
                val request = Request.Builder()
                    .addHeader("Authorization", "token " + Login.token)
                    .url(BuildConfig.CHANGE_PASS)
                    .post(body)
                    .build()

                val response = client.newCall(request).execute()

                val status = response.body()!!.string().split(":")[1]
                println(status.substring(1, status.length - 2))
                if (status.substring(1, status.length - 2) == "Failed") {
                    cur_pass_error.text = "current password is wrong."
                    cur_pass_error.setTextColor(Color.RED)

                } else {
                    cur_pass_error.text = ""
                    startActivity(Intent(this, MainActivity::class.java))
                }

            }

            //println(response.body()!!.string())
        }


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (!MainActivity.isLogin) {
            menu!!.findItem(R.id.logged).isVisible = false
            menu.findItem(R.id.login).isVisible = true
        } else {
            menu!!.findItem(R.id.logged).isVisible = true
            menu.findItem(R.id.login).isVisible = false
        }
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.deposit -> {
                startActivity(Intent(this, Signup::class.java))
                return true
            }
            R.id.login -> {
                startActivity(Intent(this, Login::class.java))
                return true
            }
            R.id.logged -> {
                startActivity(Intent(this, UserProfile::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}