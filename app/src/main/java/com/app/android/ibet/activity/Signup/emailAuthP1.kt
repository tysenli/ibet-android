package com.app.android.ibet.activity.Signup

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import com.app.android.ibet.R
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator


import com.app.android.ibet.activity.MainActivity
import com.wajahatkarim3.easyvalidation.core.view_ktx.minLength
import com.ybs.passwordstrengthmeter.PasswordStrength
import kotlinx.android.synthetic.main.activity_email_auth_p1.*
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.BaseAdapter







class emailAuthP1 : AppCompatActivity() {
    companion object {

        var MAIL = "mail"
        var PASS1 = "pass1"
        var LAN = "lan"

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
        var language = arrayOf("English", "Chinese", "Thai")
       // var flag[] = {R.drawable.gb, R.drawable.cn, R.drawable.th}
        var lanSpinner = findViewById<Spinner>(R.id.lang)
        if (lanSpinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,language)
                //CusAdapter(this, flag, language)
            lanSpinner.adapter = arrayAdapter

            lanSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    //Toast.makeText(this, getString(R.string.selected_item) + " " + gender[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }

        }


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
            val lan = lang.selectedItem.toString()
            val res = Intent(applicationContext, emailAuthP2::class.java)

            res.putExtra(MAIL,mail)
            res.putExtra(PASS1,pass1)
            res.putExtra(LAN, lan)
            //res.putExtra(emailAuthP1.PASS2,pass1)
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
    class CusAdapter (
        internal var context: Context,
        internal var flags: Array<Int>,
        internal var language: Array<String>
    ) : BaseAdapter() {
        internal var inflter: LayoutInflater

        init {
            inflter = LayoutInflater.from(context)
        }

        override fun getCount(): Int {
            return flags.size
        }

        override fun getItem(i: Int): Any? {
            return null
        }

        override fun getItemId(i: Int): Long {
            return 0
        }

        override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
            val view = inflter.inflate(R.layout.custom_spinner_items, null)
            val icon = view.findViewById<View>(R.id.imageView) as ImageView
            val names = view.findViewById<View>(R.id.textView) as TextView
            icon.setImageResource(flags[i])
            names.text = language[i]
            return view
        }
    }







}
