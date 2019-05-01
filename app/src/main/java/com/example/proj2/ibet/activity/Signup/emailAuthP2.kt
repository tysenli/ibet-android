package com.example.proj2.ibet.activity.Signup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.example.proj2.ibet.R
import com.example.proj2.ibet.activity.MainActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.hbb20.CountryCodePicker
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_email_auth_p1.*
import kotlinx.android.synthetic.main.activity_email_auth_p2.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
//import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import java.net.HttpURLConnection.HTTP_OK
//import com.sun.xml.internal.ws.streaming.XMLStreamWriterUtil.getOutputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


class emailAuthP2: AppCompatActivity(),CountryCodePicker.OnCountryChangeListener  {
    private var ccp: CountryCodePicker?=null
    private var countryCode:String?=null
    private var countryName:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_auth_p2)
        //phone_area
        ccp = findViewById<com.hbb20.CountryCodePicker>(R.id.country_code_picker)
        ccp!!.setOnCountryChangeListener(this)

        //to set default country code as Japan
        //ccp!!.setDefaultCountryUsingNameCode("JP")
        phone.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                phone.validator().nonEmpty().addErrorCallback {
                    phone.error = it
                }.check()
            }

        })
        //first_name
        first_name.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                first_name.validator().nonEmpty().addErrorCallback {
                    first_name.error = it
                }.check()
            }

        })
        //last_name
        last_name.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                last_name.validator().nonEmpty().addErrorCallback {
                    last_name.error = it
                }.check()
            }

        })
        //title
        var title = arrayOf("Mr.", "Mrs.", "Ms.")
        var titleSpinner = findViewById<Spinner>(R.id.title)
        if (titleSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, title)
            titleSpinner.adapter = arrayAdapter

            titleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    //Toast.makeText(this, getString(R.string.selected_item) + " " + gender[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }

        }
        //gender
        val gender = arrayOf("male", "female")
        val genderSpinner = findViewById<Spinner>(R.id.gender)
        if (genderSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gender)
            genderSpinner.adapter = arrayAdapter

            genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    //Toast.makeText(this, getString(R.string.selected_item) + " " + gender[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }

        }
        // country
        val locales = Locale.getAvailableLocales();
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country = locale.displayCountry
            if (country.trim { it <= ' ' }.length > 0 && !countries.contains(country)) {
                countries.add(country)
            }
        }

        Collections.sort(countries)
        val countrySpinner = findViewById<Spinner>(R.id.country)
        if (countrySpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
            countrySpinner.adapter = arrayAdapter
        }
        //city
        city.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                city.validator().nonEmpty().addErrorCallback {
                    city.error = it
                }.check()
            }

        })
        //zipcode
        zipcode.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                zipcode.validator().nonEmpty().addErrorCallback {
                    zipcode.error = it
                }.check()
            }

        })
        //currency
        val currency = arrayOf("GBP", "CAD","EUR","RMB","USD")
        val currencySpinner = findViewById<Spinner>(R.id.currency)
        if (currencySpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currency)
            currencySpinner.adapter = arrayAdapter
        }
        //odds
        val odds = arrayOf("Decimal Odds","Fractional Odds", "US Odds")
        val oddsSpinner = findViewById<Spinner>(R.id.odds_display)
        if (oddsSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, odds)
            oddsSpinner.adapter = arrayAdapter
        }
        //prefer team
        val prefer = arrayOf("01","02","03")
        val preferSpinner = findViewById<Spinner>(R.id.team)
        if (preferSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, prefer)
            preferSpinner.adapter = arrayAdapter
        }
        //deposit limit - time
        val deTime = arrayOf("1 week", "1 month","1 year")
        val timeSpinner = findViewById<Spinner>(R.id.time_period)
        if (timeSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, deTime)
            timeSpinner.adapter = arrayAdapter
        }
        //deposit limit - amount
        val amount = arrayOf("1 hundred", "1 thousand","1 million","1 billion")
        val amountSpinner = findViewById<Spinner>(R.id.amount)
        if (amountSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, amount)
            amountSpinner.adapter = arrayAdapter
        }




        var signupJson =
            """
            {
             "username":"Jiaqi",
             "password1":"Lub13080*",
	            "password2":"Lub13080*",
	            "email":"jiaqi@test.com",
	            "phone": "3142004001",
	            "first_name": "jiaqi",
	            "last_name": "hu",
	            "title": "Mr.",
	            "gender": "male",
	            "date_of_birth": "02/02/1996",
	            "country": "United States",
	            "street_address_1": "123 street",
	            "street_address_2": "123",
	            "city": "san jose",
	            "state": "ca",
	            "zipcode": "95954",
	            "preferred_team": "01",
	            "over_eighteen": true,
	            "contact_option":"SMS",

            }
            """


        signup2.setOnClickListener {
            /*
            val signupJson = JSONObject()
            signupJson.put("username" , "Jiaqi")
            signupJson.put("password1", "LUB13080*")
            signupJson.put("password2", "LUB13080*")
            signupJson.put("email"    , "jiaqi@test.com")
            signupJson.put("phone"    , "3143143131")
            signupJson.put("first_name" , "Jennie")
            signupJson.put("last_name", "Hu")
            signupJson.put("title"    , "Ms.")
            signupJson.put("gender"   , "female")
            signupJson.put("date_of_birth" , "04/07/1996")
            signupJson.put("country"  , "United States")
            signupJson.put("street_address_1" , "test street")
            signupJson.put("street_address_2" , "123")
            signupJson.put("city"     , "saint louis")
            signupJson.put("state"    , "MO")
            signupJson.put("zipcode"  , "63130")
            signupJson.put("preferred_team" , "01")
            signupJson.put("over_eighteen" , true) */
            println(signupJson)

            val (request, response, result) = Fuel.post("http://127.0.0.1:8000/users/api/signup")
                .body(signupJson)
                .responseString()

            result.fold(success = {
                println(it.toString())
            }, failure = {
                println(String(it.errorData))
            })
            //post(signupJson)
            startActivity(Intent(applicationContext, emailAuthP3::class.java))

        }
        close2.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))

        }
    }
    override fun onCountrySelected() {
        countryCode=ccp!!.selectedCountryCode
        countryName=ccp!!.selectedCountryName

        Toast.makeText(this,"Country Code "+countryCode,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,"Country Name "+countryName,Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in Toast
            //Toast.makeText(this, """$dayOfMonth - ${monthOfYear + 1} - $year""", Toast.LENGTH_LONG).show()
            birth_show.setText("""$dayOfMonth - ${monthOfYear + 1} - $year""")

        }, year, month, day)
        dpd.show()
    }

    private fun post(signupJson : String) {
        val (request, response, result) = Fuel.post("http://127.0.0.1:8000/users/api/signup")
            .body(signupJson)
            .response()
    }




}