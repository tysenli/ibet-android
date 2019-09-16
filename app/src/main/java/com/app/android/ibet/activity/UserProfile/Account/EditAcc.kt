package com.app.android.ibet.activity.UserProfile.Account

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import com.app.android.ibet.api.Api
import com.wajahatkarim3.easyvalidation.core.view_ktx.minLength
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.frag_account.*
import kotlinx.android.synthetic.main.frag_account.acc_first_name
import kotlinx.android.synthetic.main.frag_account.acc_last_name
import kotlinx.android.synthetic.main.frag_account_edit.*
import kotlinx.android.synthetic.main.frag_account_edit.btn_save
import kotlinx.android.synthetic.main.frag_account_edit.cur_pass_error
import kotlinx.android.synthetic.main.frag_account_edit.new_pass_error
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class EditAcc : Fragment() {
    private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_account_edit, container, false)
    }
    override fun onStart() {
        super.onStart()
        cur_pass_error.text = ""
        new_pass_error.text = ""
        match_error.text = ""
        var userData = Api().get(BuildConfig.USER)
        //println(userData)
        acc_editid.text = "ID: " + JSONObject(userData).getString("pk")
        acc_first_name.text = JSONObject(userData).getString("first_name")
        acc_last_name.text = JSONObject(userData).getString("last_name")
        acc_edit_username.hint = JSONObject(userData).getString("username")
        acc_edit_email.text = JSONObject(userData).getString("email")
        acc_edit_phone.hint = JSONObject(userData).getString("phone")
        val time = JSONObject(userData).getString("time_of_registration").substring(5,7) + "/" +
                JSONObject(userData).getString("time_of_registration").substring(8,10) + "/" +
                JSONObject(userData).getString("time_of_registration").substring(0,4)
        acc_edit_time.text = time

        if (JSONObject(userData).getString("country").isEmpty()) {
            acc_edit_country.hint = "Country"
        } else {
            acc_edit_country.hint = JSONObject(userData).getString("country")
        }
        if (JSONObject(userData).getString("street_address_1").isEmpty()) {
            acc_edit_street.hint = "Street"
        } else {
            acc_edit_street.hint = JSONObject(userData).getString("street_address_1")
        }
        if (JSONObject(userData).getString("city").isEmpty()) {
            acc_edit_city.hint = "City"
        } else {
            acc_edit_city.hint = JSONObject(userData).getString("city")
        }
        if (JSONObject(userData).getString("city").isEmpty()) {
            acc_edit_code.hint = "Zipcode"
        } else {
            acc_edit_code.hint = JSONObject(userData).getString("zipcode")
        }

        btn_cancel.setOnClickListener {
            info = "acc"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        btn_save.setOnClickListener {
            val editJson = JSONObject()

            editJson.put("email"            , JSONObject(userData).getString("email"))
            if (acc_edit_username.text.isEmpty()) {
                editJson.put("username"     , JSONObject(userData).getString("username"))
            } else {
                editJson.put("username"     , acc_edit_username.text.toString())
            }
            if (acc_edit_phone.text.isEmpty()) {
                editJson.put("phone"        , JSONObject(userData).getString("phone"))
            } else {
                editJson.put("phone"        , acc_edit_phone.text.toString())
            }
            if (JSONObject(userData).getString("first_name").isEmpty()) {
                editJson.put("first_name","first name")
            } else {
                editJson.put("first_name", JSONObject(userData).getString("first_name"))
            }
            if (JSONObject(userData).getString("last_name").isEmpty()) {
                editJson.put("last_name","last name")
            } else {
                editJson.put("last_name", JSONObject(userData).getString("last_name"))
            }
            if (JSONObject(userData).getString("date_of_birth").isEmpty()) {
                editJson.put("date_of_birth", "12/12/1996")
            } else {
                editJson.put("date_of_birth", JSONObject(userData).getString("date_of_birth"))
            }
            if (acc_edit_country.text.isEmpty()) {
                if (JSONObject(userData).getString("country").isEmpty()) {
                    editJson.put("country","Country")
                } else {
                    editJson.put("country", JSONObject(userData).getString("country"))
                }
            } else {
                editJson.put("country", acc_edit_country.text.toString())
            }
            if (acc_edit_street.text.isEmpty()) {
                if (JSONObject(userData).getString("street_address_1").isEmpty()) {
                    editJson.put("street_address_1","street")
                } else {
                    editJson.put("street_address_1", JSONObject(userData).getString("street_address_1"))
                }
            } else {
                editJson.put("street_address_1", acc_edit_street.text.toString())
            }
            if (acc_edit_city.text.isEmpty()) {
                if (JSONObject(userData).getString("city").isEmpty()) {
                    editJson.put("city","City")
                } else {
                    editJson.put("city", JSONObject(userData).getString("city"))
                }

            } else {
                editJson.put("city", acc_edit_city.text.toString())
            }
            if (acc_edit_code.text.isEmpty()) {
                if (JSONObject(userData).getString("zipcode").isEmpty()) {
                    editJson.put("zipcode","Zipcode")
                } else {
                    editJson.put("zipcode", JSONObject(userData).getString("zipcode"))
                }
            } else {
                editJson.put("zipcode", acc_edit_code.text.toString())
            }
            //editJson.put("over_eighteen"    , true)
            val client = OkHttpClient()

            val JSON = MediaType.get("application/json; charset=utf-8")
            val body = RequestBody.create(JSON, editJson.toString())
            val request = Request.Builder()
                .header("Authorization", "Token "+ Login.token)
                .url(BuildConfig.USER)
                .put(body)
                .build()

            val response = client.newCall(request).execute()
           // println(response.code())
           // println("this is:" + response.body()!!.string())

            //change pass
            if (acc_edit_curpass.text.isNotEmpty()) {
                if (!acc_edit_newpass.minLength(8)) {
                    new_pass_error.text = "Your password must include at least 8 charachters."
                    cur_pass_error.text = ""
                } else if (acc_edit_newpass.text.toString()!= acc_edit_confirmpass.text.toString()) {
                    new_pass_error.text = ""
                    match_error.text = "Your password does not match."
                }
                else {
                    new_pass_error.text = ""
                    match_error.text = ""
                    var changeJson = JSONObject()
                    changeJson.put("current_password", acc_edit_curpass.text.toString())
                    changeJson.put("new_password", acc_edit_newpass.text.toString())

                    val client = OkHttpClient()
                    val JSON = MediaType.get("application/json; charset=utf-8")
                    val body = RequestBody.create(JSON, changeJson.toString())
                    val request = Request.Builder()
                        .addHeader("Authorization", "token " + Login.token)
                        .url(BuildConfig.CHANGE_PASS)
                        .post(body)
                        .build()

                    val response = client.newCall(request).execute()
                    if (response.code() == 400) {
                        cur_pass_error.text = "Incorrect password. Please, try again."
                    }
                    else {
                        info = "acc"
                        Account.isChange = true
                        startActivity(Intent(activity, MyAccount::class.java))
                        activity!!.overridePendingTransition(0, 0)
                    }


                }
            } else {
                info = "acc"
                Account.isChange = true
                startActivity(Intent(activity, MyAccount::class.java))
                activity!!.overridePendingTransition(0, 0)
            }

        }

    }
}