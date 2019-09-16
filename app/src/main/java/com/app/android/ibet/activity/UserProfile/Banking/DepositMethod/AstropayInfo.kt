package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.Banking.Deposit
import com.app.android.ibet.api.Api
import com.app.android.ibet.api.URLs
import kotlinx.android.synthetic.main.activity_amount_input.*
import kotlinx.android.synthetic.main.activity_amount_input.amount_display
import kotlinx.android.synthetic.main.activity_amount_input.deposit_amount2

import kotlinx.android.synthetic.main.frag_astropay.*

import kotlinx.android.synthetic.main.frag_astropay.amt_input_err
import kotlinx.android.synthetic.main.frag_astropay.btn_wechat_dep
import kotlinx.android.synthetic.main.frag_astropay.change_method
import kotlinx.android.synthetic.main.frag_astropay.code_err
import kotlinx.android.synthetic.main.frag_astropay.date_err
import kotlinx.android.synthetic.main.frag_astropay.depo_method_show
import kotlinx.android.synthetic.main.frag_astropay.money_100
import kotlinx.android.synthetic.main.frag_astropay.money_25
import kotlinx.android.synthetic.main.frag_astropay.money_250
import kotlinx.android.synthetic.main.frag_astropay.money_50
import kotlinx.android.synthetic.main.frag_astropay.num_err
import kotlinx.android.synthetic.main.frag_bt.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject


class AstropayInfo: Fragment() {
    //private var parentContext = context
    companion object {
        var cardnum = ""
        var carddate = ""
        var cvv = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_astropay, container, false)
    }

    override fun onStart() {
        super.onStart()
        depo_method_show.background = resources.getDrawable(R.drawable.astropay)
        num_err.visibility = View.GONE
        date_err.visibility = View.GONE
        code_err.visibility =  View.GONE
        amt_input_err.visibility = View.GONE
        money_25.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(201, 199, 199))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            deposit_amount.setText(money_25.text)
            MyAccount.depo_amt = deposit_amount.text.toString()

        }
        money_50.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(201, 199, 199))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            deposit_amount.setText(money_50.text)
            MyAccount.depo_amt = deposit_amount.text.toString()

        }
        money_100.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(201, 199, 199))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            deposit_amount.setText(money_100.text)
            MyAccount.depo_amt = deposit_amount.text.toString()

        }
        money_250.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(201, 199, 199))
            deposit_amount.setText(money_250.text)
            MyAccount.depo_amt = deposit_amount.text.toString()
        }

        deposit_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                MyAccount.depo_amt = deposit_amount.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                money_25.setBackgroundColor(Color.rgb(239, 239, 239))
                money_50.setBackgroundColor(Color.rgb(239, 239, 239))
                money_100.setBackgroundColor(Color.rgb(239, 239, 239))
                money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            }

        })

        change_method.setOnClickListener {
            MyAccount.info = "deposit"
            val intent = Intent(activity, MyAccount::class.java)
            startActivity(intent)
            activity!!.overridePendingTransition(0, 0)
        }

        btn_wechat_dep.setOnClickListener {
            cardnum = card_num.text.toString()
            carddate = card_time.text.toString()
            cvv = card_code.text.toString()

            if (cardnum.isEmpty() or carddate.isEmpty() or cvv.isEmpty() ) {

                if (cardnum.isEmpty())  {
                    card_num.background = resources.getDrawable(R.drawable.border3)
                    num_err.visibility = View.VISIBLE
                } else{
                    card_num.background = resources.getDrawable(R.drawable.border)
                    num_err.visibility = View.GONE
                }
                if (carddate.isEmpty()) {
                    card_time.background = resources.getDrawable(R.drawable.border3)
                    date_err.visibility = View.VISIBLE
                } else {
                    card_time.background = resources.getDrawable(R.drawable.border)
                    date_err.visibility = View.GONE
                }
                if (cvv.isEmpty()) {
                    card_code.background = resources.getDrawable(R.drawable.border3)
                    code_err.visibility = View.VISIBLE
                } else {
                    card_code.background = resources.getDrawable(R.drawable.border)
                    code_err.visibility = View.GONE
                }

            } else {
                if (deposit_amount.text.toString() == "" || deposit_amount.text.toString().toFloat() < 10 || deposit_amount.text.toString().toFloat() > 2000) {
                    amt_input_err.visibility = View.VISIBLE
                } else {
                    amt_input_err.visibility = View.GONE
                    val client = OkHttpClient()
                    val astroJson = JSONObject()
                    val JSON = MediaType.get("application/json; charset=utf-8")
                    //println("hhh" + cardnum)
                    astroJson.put("card_num", cardnum)
                    astroJson.put("card_code", cvv)
                    astroJson.put("exp_date", carddate)
                    astroJson.put("amount", deposit_amount.text.toString())
                    val body = RequestBody.create(JSON, astroJson.toString())
                    val request = Request.Builder()
                        .addHeader("Authorization", "Token " + Login.token)
                        .url(URLs.ASTROPAY)
                        .post(body)
                        .build()
                    val response = client.newCall(request).execute()
                    if (response.code() != 200) {
                        MyAccount.info = "fail"
                        val res = Intent(context, MyAccount::class.java)
                        startActivity(res)
                    } else {
                        val statusData = response.body()!!.string()
                        Log.e("Astropay", statusData)
                        //println(JSONObject(statusData).getString("response_msg").substring(0,6))

                        if (JSONObject(statusData).getString("response_msg").substring(0, 6) == "1|1|1|") {
                            val user = JSONObject(MyAccount.userData).getString("username")
                            val depositJson = JSONObject()
                            depositJson.put("type", "add")
                            depositJson.put("username", user)
                            depositJson.put("balance", deposit_amount.text.toString())
                            val balance = Api().post(depositJson.toString(), URLs.BALANCE)
                            MyAccount.info = "success"
                            val res = Intent(context, MyAccount::class.java)
                            //res.putExtra("amount",intent.getStringExtra("balance"))
                            startActivity(res)

                        } else {
                            MyAccount.info = "fail"
                            val res = Intent(context, MyAccount::class.java)
                            startActivity(res)
                        }
                    }
                }
            }

        }
/*
        visa_continue.setOnClickListener {
            cardnum = card_num.text.toString()
            carddate = card_time.text.toString()
            cvv = card_code.text.toString()

            if (cardnum.isEmpty() or carddate.isEmpty() or cvv.isEmpty() or card_name.text.isEmpty() ) {

                    if (cardnum.isEmpty())  {
                        card_num.background = resources.getDrawable(R.drawable.border3)
                        num_err.visibility = View.VISIBLE
                    } else{
                        card_num.background = resources.getDrawable(R.drawable.border)
                        num_err.visibility = View.GONE
                    }
                    if (carddate.isEmpty()) {
                        card_time.background = resources.getDrawable(R.drawable.border3)
                        date_err.visibility = View.VISIBLE
                    } else {
                        card_time.background = resources.getDrawable(R.drawable.border)
                        date_err.visibility = View.GONE
                    }
                    if (cvv.isEmpty()) {
                        card_code.background = resources.getDrawable(R.drawable.border3)
                        code_err.visibility = View.VISIBLE
                    } else {
                        card_code.background = resources.getDrawable(R.drawable.border)
                        code_err.visibility = View.GONE
                    }
                    if (card_name.text.isEmpty())  {
                        card_name.background = resources.getDrawable(R.drawable.border3)
                        name_err.visibility = View.VISIBLE
                    } else {
                        card_name.background = resources.getDrawable(R.drawable.border)
                        name_err.visibility = View.GONE
                    }

            } else {
                MyAccount.info = "astropay_input"
                startActivity(Intent(activity, MyAccount::class.java))
                activity!!.overridePendingTransition(0, 0)
            }

        } */

    }

}
