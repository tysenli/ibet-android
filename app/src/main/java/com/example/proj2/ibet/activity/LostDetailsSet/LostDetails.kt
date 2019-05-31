package com.example.proj2.ibet.activity.LostDetailsSet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import kotlinx.android.synthetic.main.lost_details.*
import android.R
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import java.security.cert.CertPathValidator


class LostDetails : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.example.proj2.ibet.R.layout.lost_details)

        val emailOrPhoneText = findViewById<EditText>(com.example.proj2.ibet.R.id.email_phone_text)

        val isPhoneText: Boolean? = false

        fun EditText.afterTextChanged (afterTextChange: (String) -> Unit) {
            this.addTextChangedListener((object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    afterTextChange.invoke(s.toString())
                    send_code.isEnabled = s.toString().isNotBlank()

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            }))
        }

        fun EditText.validate(message: String, validator: (String) -> Boolean) {
            this.afterTextChanged {
                this.error = if (validator(it)) null else message
            }
            this.error = if (validator(this.text.toString())) null else message
        }

        fun String.isValidEmail(): Boolean
        = this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

        fun String.isValidMobile() : Boolean
        = this.isNotBlank() && Patterns.PHONE.matcher(this).matches()

        fun String.isEmail()
        = this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

//        email_phone_text.validate("Valid email address required") {s -> s.isValidEmail()}
        email_phone_text.validate("Valid phone number required") {s -> s.isValidMobile()}


        send_code.setOnClickListener{view ->
            var intent = Intent(this@LostDetails, ConfirmPhoneNumber::class.java)
            startActivity(intent)
        }

        forgot_username.setOnClickListener{ view ->
            var intent = Intent(this@LostDetails, LostUsername::class.java)
            startActivity(intent)
        }





    }
}