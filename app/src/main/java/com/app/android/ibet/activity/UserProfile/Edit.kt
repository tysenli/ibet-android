package com.app.android.ibet.activity.UserProfile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.city_edit
import kotlinx.android.synthetic.main.activity_edit_profile.edit_address
import kotlinx.android.synthetic.main.activity_edit_profile.zip_code_edit
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class Edit : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_profile)
        val request = Request.Builder()
            .header("Authorization", "Token "+ Login.token)
            .url(BuildConfig.USER)
            .build()
        val response = OkHttpClient().newCall(request).execute()
        println(Login.token)
        //println(response.body()!!.string())
        var jsonData = response.body()!!.string()
        println(jsonData)
        username_edit.hint = JSONObject(jsonData).getString("username")
        first_name_edit.hint = JSONObject(jsonData).getString("first_name")
        last_name_edit.hint = JSONObject(jsonData).getString("last_name")
        mm_edit.hint = JSONObject(jsonData).getString("date_of_birth").split("/")[0]
        dd_edit.hint = JSONObject(jsonData).getString("date_of_birth").split("/")[1]
        yy_edit.hint = JSONObject(jsonData).getString("date_of_birth").split("/")[2].substring(0,4)
        email_edit.hint = JSONObject(jsonData).getString("email")
        phone_edit.hint = JSONObject(jsonData).getString("phone")
        btn_save.setOnClickListener {
            val editJson = JSONObject()

            editJson.put("email"            , JSONObject(jsonData).getString("email"))
            if (phone_edit.text.isEmpty()) {
                editJson.put("phone"        , JSONObject(jsonData).getString("phone"))
            } else {
                editJson.put("phone"        , phone_edit.text.toString())
            }
            editJson.put("first_name"       , JSONObject(jsonData).getString("first_name"))
            editJson.put("last_name"        , JSONObject(jsonData).getString("last_name"))
            editJson.put("date_of_birth"    , JSONObject(jsonData).getString("date_of_birth"))
            editJson.put("country"          , JSONObject(jsonData).getString("country"))
            if (edit_address.text.isEmpty()) {
                editJson.put("street_address_1", JSONObject(jsonData).getString("street_address_1"))
            } else {
                editJson.put("street_address_1", edit_address.text.toString())
            }
            if (city_edit.text.isEmpty()) {
                editJson.put("city", JSONObject(jsonData).getString("city"))

            } else {
                editJson.put("city", city_edit.text.toString())
            }
            if (zip_code_edit.text.isEmpty()) {
                editJson.put("zipcode", JSONObject(jsonData).getString("zipcode"))
            } else {
                editJson.put("zipcode", zip_code_edit.text.toString())
            }
            //editJson.put("over_eighteen"    , true)
            val client = OkHttpClient()

            val JSON = MediaType.get("application/json; charset=utf-8")
            val body = RequestBody.create(JSON, editJson.toString())
            val request = Request.Builder()
                .header("Authorization", "Token "+ Login.token)
                .url(BuildConfig.USER)
                .put(body)
                .build()

            val response = client.newCall(request).execute()

            //println(response.request())
            println("this is:" + response.body()!!.string())
        }
    }
}