package com.example.proj2.ibet.activity.Signup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.proj2.ibet.R
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_email_auth.*


class emailAuthP1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_auth)
        var emailStr = email.text.toString()
        //var passwordStr = password.text.toString()

        var passwordtxt = findViewById<EditText>(R.id.password)
        emailStr.validEmail() {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        passwordtxt.validator().minLength(8).addErrorCallback {
            passwordtxt.error = it
        }.check()

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

        continue1

    }







}
