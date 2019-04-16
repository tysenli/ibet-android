package com.example.proj2.ibet.activity.Signup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_email_auth_p1.*
import android.R.attr.password
import android.R
import com.ybs.passwordstrengthmeter.PasswordStrength


class emailAuthP1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.proj2.ibet.R.layout.activity_email_auth_p1)
        var emailtxt = findViewById<EditText>(com.example.proj2.ibet.R.id.email)
        //var passwordStr = password.text.toString()

        var passwordtxt = findViewById<EditText>(com.example.proj2.ibet.R.id.password)

        emailtxt.addTextChangedListener (object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                emailtxt.validator().validEmail().addErrorCallback {
                    emailtxt.error = it
                }.check()
            }

        })

        passwordtxt.addTextChangedListener (object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passwordtxt.validator().minLength(8).addErrorCallback {
                    passwordtxt.error = it
                }.check()
                updatePasswordStrenth(s.toString())
            }

        })

        checkBox1.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (!isChecked) {
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
        }

        checkBox2.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (!isChecked) {
                con_password.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                con_password.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
        }

        continue1.setOnClickListener {
            startActivity(Intent(applicationContext, emailAuthP2::class.java))
        }

    }

    private fun updatePasswordStrenth(password : String) {
        var progressBar = findViewById<ProgressBar>(com.example.proj2.ibet.R.id.progressBar)
        var strengthView = findViewById<TextView>(com.example.proj2.ibet.R.id.password_strength)
        if (TextView.VISIBLE != strengthView.visibility) {
            return
        }
        if (password.isEmpty()) {
            strengthView.setText("")
            progressBar.setProgress(0)
            return

        }
        val str = PasswordStrength.calculateStrength(password)
        strengthView.setText(str.getText(this))
        strengthView.setTextColor(str.getColor())

        progressBar.progressDrawable.setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN)
        if (str.getText(this).equals("Weak")) {
            progressBar.progress = 25
        } else if (str.getText(this).equals("Medium")) {
            progressBar.progress = 50
        } else if (str.getText(this).equals("Strong")) {
            progressBar.progress = 75
        } else {
            progressBar.progress = 100
        }
    }







}
