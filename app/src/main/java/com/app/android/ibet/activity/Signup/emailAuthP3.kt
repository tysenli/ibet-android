package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_email_auth_p3.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class emailAuthP3: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_auth_p3)
        /*
        close3.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))

        } */
        val locales = Locale.getAvailableLocales();
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country = locale.displayCountry
            if (country.trim { it <= ' ' }.length > 0 && !countries.contains(country)) {
                countries.add(country)
            }
        }
        Collections.sort(countries)
        countries.add(0,"COUNTRY")

        val countrySpinner = findViewById<Spinner>(R.id.country_id)
        if (countrySpinner != null) {
            var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,countries)
            arrayAdapter.add("Country")
            countrySpinner.adapter = arrayAdapter
        }

        country_id.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                country_id.prompt = "Country"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }
        signup3.setOnClickListener {
            val signupJson = JSONObject()
            //println("this is birth: " + birth_show.text.toString())
            signupJson.put("username"         , intent.getStringExtra(emailAuthP2.USER))
            signupJson.put("password"        ,  intent.getStringExtra(emailAuthP1.PASS1))
            signupJson.put("email"            , intent.getStringExtra(emailAuthP1.MAIL))
            signupJson.put("phone"            , (0..10).random().toString())
            signupJson.put("first_name"       , intent.getStringExtra(emailAuthP2.FIRST))
            signupJson.put("last_name"        , intent.getStringExtra(emailAuthP2.LAST))
            signupJson.put("date_of_birth"    , intent.getStringExtra(emailAuthP2.BIRTH))
            signupJson.put("country"          , country_id.selectedItem.toString())
            signupJson.put("street_address_1" , address.text.toString())
            signupJson.put("city"             , city.text.toString())
            signupJson.put("zipcode"          , zip_code.text.toString())

            signupJson.put("over_eighteen"    , true)

            //val url = "http://10.0.2.2:8000/users/api/signup/"
            post(signupJson.toString(), BuildConfig.SIGNUP_URL)
            startActivity(Intent(applicationContext, Verify::class.java))

        }
    }
    fun post(json : String, url : String){

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
        println("this is:" + response.body()!!.string())

    }
}