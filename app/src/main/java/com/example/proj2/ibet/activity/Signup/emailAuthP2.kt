package com.example.proj2.ibet.activity.Signup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.proj2.ibet.R
import kotlinx.android.synthetic.main.activity_email_auth_p1.*
import kotlinx.android.synthetic.main.activity_email_auth_p2.*
import java.text.SimpleDateFormat
import java.util.*

class emailAuthP2: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_auth_p2)
        val gender = arrayOf("male", "female")
        val genderSpinner = findViewById<Spinner>(R.id.gender)
        if (genderSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gender)
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
        continue2.setOnClickListener {
            startActivity(Intent(applicationContext, emailAuthP3::class.java))
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in Toast
            //Toast.makeText(this, """$dayOfMonth - ${monthOfYear + 1} - $year""", Toast.LENGTH_LONG).show()
            birth_show.setText("""$dayOfMonth - ${monthOfYear + 1} - $year""")

        }, year, month, day)
        dpd.show()
    }

        /*
        val tv_birth: TextView = findViewById(R.id.birth)
        tv_birth.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            tv_birth.text = sdf.format(cal.time)

        }

        tv_birth.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }*/


}