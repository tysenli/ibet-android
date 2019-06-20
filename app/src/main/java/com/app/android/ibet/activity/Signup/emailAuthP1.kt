package com.app.android.ibet.activity.Signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.app.android.ibet.R
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator


import com.app.android.ibet.activity.MainActivity
import com.wajahatkarim3.easyvalidation.core.view_ktx.minLength
import com.ybs.passwordstrengthmeter.PasswordStrength
import kotlinx.android.synthetic.main.activity_email_auth_p1.*




class emailAuthP1 : AppCompatActivity() {
    companion object {
        val USER = "user"
        var MAIL = "mail"
        var PASS1 = "pass1"
        var PASS2 = "pass2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.app.android.ibet.R.layout.activity_email_auth_p1)
       // var usertxt = findViewById<EditText>(com.app.android.ibet.R.id.username_id)
        var emailtxt = findViewById<EditText>(com.app.android.ibet.R.id.email_id)
        var passwordtxt = findViewById<EditText>(com.app.android.ibet.R.id.password_id)
        //var conPasswordtxt = findViewById<EditText>(com.app.android.ibet.R.id.con_password_id)

        var isValidUser = false
        var isValidEmail = false
        var isValidPassword = false

        continue1.setEnabled(false)
        /*
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
        */
        emailtxt.addTextChangedListener (object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (emailtxt.validEmail()) {
                    isValidEmail = true
                }


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (emailtxt.validEmail()) {
                    tv_email2.text = "This will be used to log in."
                    tv_email2.setTextColor(Color.rgb(116,113,117))

                } else {
                    tv_email2.text = "Email address not valid"
                    tv_email2.setTextColor(Color.rgb(255,0,0))
                }
                /*
                emailtxt.validator().validEmail().addErrorCallback {
                    emailtxt.error = it
                }.check()*/
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
                if (passwordtxt.minLength(8)) {
                    tv_password2.text = "At least 8 character."
                    tv_password2.setTextColor(Color.rgb(116,113,117))

                } else {
                    tv_password2.text = "Password too simple"
                    tv_password2.setTextColor(Color.rgb(255,0,0))
                }


                /*
                passwordtxt.validator().minLength(8).addErrorCallback {
                    passwordtxt.error = it
                }.check() */
                updatePasswordStrenth(s.toString())
                if (isValidEmail && passwordtxt.minLength(8)) {
                    continue1.setBackgroundResource(R.drawable.btn_red)
                    continue1.setEnabled(true)
                } else {
                    continue1.setBackgroundResource(R.drawable.btn_red2)
                    continue1.setEnabled(false)
                }

            }

        })
        /*
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

        }) */
/*
        checkBox1.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (!isChecked) {
                password_id.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                password_id.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
        } */
        /*

        checkBox2.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (!isChecked) {
                con_password_id.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                con_password_id.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
        } */

        continue1.setOnClickListener {
            //val user = username_id.text.toString()
            val mail = email_id.text.toString()
            val pass1 = password_id.text.toString()
            //val pass2 = con_password_id.text.toString()
            val res = Intent(applicationContext, emailAuthP2::class.java)
            //println("user name:" + user)
            //res.putExtra(emailAuthP1.USER,user)
            res.putExtra(emailAuthP1.MAIL,mail)
            res.putExtra(emailAuthP1.PASS1,pass1)
            res.putExtra(emailAuthP1.PASS2,pass1)
            //startActivity(res)
           // setResult(Activity.RESULT_OK, res)
            startActivity(res)

        }

        /*
        close1.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))

        }
        */



    }

    private fun updatePasswordStrenth(password : String) {
        var progressBar = findViewById<ProgressBar>(com.app.android.ibet.R.id.progressBar)
        var strengthView = findViewById<TextView>(com.app.android.ibet.R.id.password_strength)
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
        //strengthView.setTextColor(str.getColor())
        progressBar.progressDrawable.setColorFilter(Color.rgb(255,0,0),android.graphics.PorterDuff.Mode.SRC_IN)
        //progressBar.progressDrawable.setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN)
        if (str.getText(this).equals("Password Strength: Too Weak")) {
            progressBar.progress = 25
        } else if (str.getText(this).equals("Password Strength: Weak")) {
            progressBar.progress = 50
        } else if (str.getText(this).equals("Password Strength: Great")) {
            progressBar.progress = 75
        } else {
            progressBar.progress = 100
        }
    }







}
