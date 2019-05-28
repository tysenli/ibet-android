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
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

import android.app.Activity
import com.example.proj2.ibet.activity.MainActivity
import com.wajahatkarim3.easyvalidation.core.view_ktx.minLength
import com.ybs.passwordstrengthmeter.PasswordStrength
import kotlinx.android.synthetic.main.activity_email_auth_p1.*

//import kotlinx.android.synthetic.main.activity_email_auth_p1.*


class emailAuthP1 : AppCompatActivity() {
    companion object {
        val USER = "user"
        var MAIL = "mail"
        var PASS1 = "pass1"
        var PASS2 = "pass2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.example.proj2.ibet.R.layout.activity_email_auth_p1)
        var usertxt = findViewById<EditText>(com.example.proj2.ibet.R.id.username_id)
        var emailtxt = findViewById<EditText>(com.example.proj2.ibet.R.id.email_id)
        var passwordtxt = findViewById<EditText>(com.example.proj2.ibet.R.id.password_id)
        var conPasswordtxt = findViewById<EditText>(com.example.proj2.ibet.R.id.con_password_id)

        var isValidUser = false
        var isValidEmail = false
        var isValidPassword = false
        var isValidConPassword = false

        continue1.setEnabled(false)
        usertxt.addTextChangedListener (object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
               if (usertxt.text.toString() != null) {
                   isValidUser = true
               }

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                usertxt.validator().nonEmpty().addErrorCallback {
                    usertxt.error = it
                }.check()
            }

        })
        emailtxt.addTextChangedListener (object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (emailtxt.validEmail()) {
                    isValidEmail = true
                }
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
                if (passwordtxt.minLength(8)) {
                    isValidPassword = true
                }
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
        conPasswordtxt.addTextChangedListener (object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (isValidUser && isValidEmail && isValidPassword) {
                    continue1.setEnabled(true)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                conPasswordtxt.validator().textEqualTo(passwordtxt.text.toString()).addErrorCallback {
                    conPasswordtxt.error = it
                }.check()
                if (isValidUser && isValidEmail && isValidPassword) {
                    continue1.setEnabled(true)
                }
            }

        })

        checkBox1.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (!isChecked) {
                password_id.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                password_id.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
        }

        checkBox2.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (!isChecked) {
                con_password_id.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                con_password_id.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
        }

        continue1.setOnClickListener {
            val user = username_id.text.toString()
            val mail = email_id.text.toString()
            val pass1 = password_id.text.toString()
            val pass2 = con_password_id.text.toString()
            val res = Intent(applicationContext, emailAuthP2::class.java)
            println("user name:" + user)
            res.putExtra(emailAuthP1.USER,user)
            res.putExtra(emailAuthP1.MAIL,mail)
            res.putExtra(emailAuthP1.PASS1,pass1)
            res.putExtra(emailAuthP1.PASS2,pass2)
            //startActivity(res)
           // setResult(Activity.RESULT_OK, res)
            startActivity(res)

        }

        close1.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))

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
