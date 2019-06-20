package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.activity_email_auth_p3.*
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
            startActivity(Intent(applicationContext, Verify::class.java))

        }
    }
}