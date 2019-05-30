package com.app.android.ibet.activity.Signup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import com.hbb20.CountryCodePicker
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_email_auth_p2.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*
//import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
//import com.sun.xml.internal.ws.streaming.XMLStreamWriterUtil.getOutputStream


class emailAuthP2: AppCompatActivity(),CountryCodePicker.OnCountryChangeListener  {
    private var ccp: CountryCodePicker?=null
    private var countryCode:String?=null
    private var countryName:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_auth_p2)
        val policy   = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
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
        var titleSpinner = findViewById<Spinner>(R.id.title_id)
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
        val genderSpinner = findViewById<Spinner>(R.id.gender_id)
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
        val countrySpinner = findViewById<Spinner>(R.id.country_id)
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
        val currencySpinner = findViewById<Spinner>(R.id.currency_id)
        if (currencySpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currency)
            currencySpinner.adapter = arrayAdapter
        }
        //odds
        val odds = arrayOf("Decimal Odds","Fractional Odds", "US Odds")
        val oddsSpinner = findViewById<Spinner>(R.id.odds_display_id)
        if (oddsSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, odds)
            oddsSpinner.adapter = arrayAdapter
        }
        //prefer team
        val prefer = arrayOf("01","02","03")
        val preferSpinner = findViewById<Spinner>(R.id.team_id)
        if (preferSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, prefer)
            preferSpinner.adapter = arrayAdapter
        }
        //deposit limit - time
        val deTime = arrayOf("1 week", "1 month","1 year")
        val timeSpinner = findViewById<Spinner>(R.id.time_period_id)
        if (timeSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, deTime)
            timeSpinner.adapter = arrayAdapter
        }
        //deposit limit - amount
        val amount = arrayOf("1 hundred", "1 thousand","1 million","1 billion")
        val amountSpinner = findViewById<Spinner>(R.id.amount_id)
        if (amountSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, amount)
            amountSpinner.adapter = arrayAdapter
        }

        //signup
        /*
        var signupJson =
            """
            {
             "username"         : jiaqi,
             "password1"        : "Lub13080*",
             "password2"        : "Lub13080*",
             "email"            : "jiaqi@test.com",
             "phone"            : "3142004001",
             "first_name"       : "jiaqi",
             "last_name"        : "hu",
             "title"            : "Mr.",
             "gender"           : "male",
             "date_of_birth"    : "02/02/1996",
             "country"          : "United States",
             "street_address_1" : "123 street",
             "street_address_2" : "123",
             "city"             : "san jose",
             "state"            : "ca",
             "zipcode"          : "95954",
             "preferred_team"   : "01",
             "over_eighteen"    : true,
             "contact_option"   :"SMS"
            }
        """  */
        //println(signupJson)

        signup2.setOnClickListener {
            //println("hhh" + address_line1.text.toString())
            val signupJson = JSONObject()
            //println("this is birth: " + birth_show.text.toString())
            signupJson.put("username"         , intent.getStringExtra(emailAuthP1.USER))
            signupJson.put("password1"        , intent.getStringExtra(emailAuthP1.PASS1))
            signupJson.put("password2"        , intent.getStringExtra(emailAuthP1.PASS2))
            signupJson.put("email"            , intent.getStringExtra(emailAuthP1.MAIL))
            signupJson.put("phone"            , phone.text.toString())
            signupJson.put("first_name"       , first_name.text.toString())
            signupJson.put("last_name"        , last_name.text.toString())
            signupJson.put("title"            , title_id.selectedItem.toString())
            signupJson.put("gender"           , gender_id.selectedItem.toString())
            signupJson.put("date_of_birth"    , birth_show.text.toString())
            signupJson.put("country"          , country_id.selectedItem.toString())
            signupJson.put("street_address_1" , address_line1.text.toString())
            signupJson.put("street_address_2" , address_line2.text.toString())
            signupJson.put("city"             , city.text.toString())
            signupJson.put("state"            , state_id.text.toString())
            signupJson.put("zipcode"          , zipcode.text.toString())
            signupJson.put("preferred_team"   , team_id.selectedItem.toString())
            signupJson.put("over_eighteen"    , true)
           // val url = "http://10.0.2.2:8000/users/api/signup/"

            //System.getenv("url")
            post(signupJson.toString(), BuildConfig.SIGNUP_URL)
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
            var day_dis = ""
            var month_dis = ""
            if ((dayOfMonth < 10) ){
                day_dis = "0$dayOfMonth"
                //
            } else {
                day_dis = dayOfMonth.toString()
            }
            if (monthOfYear + 1 < 10 ) {
                month_dis = "0${monthOfYear + 1}"
            } else {
                month_dis = (monthOfYear + 1).toString()
            }
            birth_show.setText(day_dis + "/" + month_dis + "/" + "$year")

        }, year, month, day)
        dpd.show()
    }

    fun post(json : String, url : String) {


        val client = OkHttpClient()

        val JSON = MediaType.get("application/json; charset=utf-8")
        val body = RequestBody.create(JSON,json)
        val request = Request.Builder()
            // .addHeader("Authorization", "Bearer $token")
            .url(url)
            .post(body)
            .build()

        val  response = client . newCall (request).execute()

       println(response.request())
       println(response.body()!!.string())

    }





}