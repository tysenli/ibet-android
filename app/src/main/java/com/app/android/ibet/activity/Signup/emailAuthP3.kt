package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_email_auth_p3.*
import kotlinx.android.synthetic.main.activity_email_auth_p3.city_edit
import kotlinx.android.synthetic.main.activity_email_auth_p3.country_id
import kotlinx.android.synthetic.main.activity_email_auth_p3.edit_address
import kotlinx.android.synthetic.main.activity_email_auth_p3.zip_code_edit
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class emailAuthP3: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
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
            signupJson.put("phone"            , phone1.text.toString())
            signupJson.put("first_name"       , intent.getStringExtra(emailAuthP2.FIRST))
            signupJson.put("last_name"        , intent.getStringExtra(emailAuthP2.LAST))
            signupJson.put("date_of_birth"    , intent.getStringExtra(emailAuthP2.BIRTH))
            signupJson.put("country"          , country_id.selectedItem.toString())
            signupJson.put("street_address_1" , edit_address.text.toString())
            signupJson.put("city"             , city_edit.text.toString())
            signupJson.put("zipcode"          , zip_code_edit.text.toString())
            signupJson.put("over_eighteen"    , true)

            //val url = "http://10.0.2.2:8000/users/api/signup/"
            post(signupJson.toString(), BuildConfig.SIGNUP_URL)
            val res = Intent(applicationContext, Verify::class.java)
            res.putExtra("user", intent.getStringExtra(emailAuthP2.USER))
            res.putExtra("email",intent.getStringExtra(emailAuthP1.MAIL))
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
    //http://10.0.2.2:8000/users/api/sendemail/?case=signup&username=test&to_email_address=jiaqi@claymore.com...
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