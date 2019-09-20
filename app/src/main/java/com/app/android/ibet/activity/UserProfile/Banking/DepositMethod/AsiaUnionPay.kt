package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.api.Api
import com.app.android.ibet.api.URLs
import kotlinx.android.synthetic.main.activity_amount_input.*
import kotlinx.android.synthetic.main.dialog.view.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class AsiaUnionPay : Fragment() {
    //private var parentContext = context
    var userData = Api().get(URLs.USER)
    var orderId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_amount_input, container, false)
    }

    override fun onStart() {
        super.onStart()
        depo_method_show.background = resources.getDrawable(R.drawable.union)
        var pk = JSONObject(userData).getString("pk")
        //println(pk)
        deposit_amount2.hint = " Deposit 100 - 4,000"
        amt_input_err.visibility = View.GONE
        money_25.text = "100"
        money_50.text = "1000"
        money_100.text = "2000"
        money_250.text = "4000"
        money_25.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(201, 199, 199))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = money_25.text
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_50.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(201, 199, 199))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = money_50.text
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_100.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(201, 199, 199))
            money_250.setBackgroundColor(Color.rgb(239, 239, 239))
            amount_display.text = money_100.text
            MyAccount.depo_amt = amount_display.text.toString()

        }
        money_250.setOnClickListener {
            money_25.setBackgroundColor(Color.rgb(239, 239, 239))
            money_50.setBackgroundColor(Color.rgb(239, 239, 239))
            money_100.setBackgroundColor(Color.rgb(239, 239, 239))
            money_250.setBackgroundColor(Color.rgb(201, 199, 199))
            amount_display.text = money_250.text
            MyAccount.depo_amt = amount_display.text.toString()
        }

        deposit_amount2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                amount_display.text = deposit_amount2.text.toString()
                MyAccount.depo_amt = amount_display.text.toString()

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
            if (amount_display.text.toString() == "" || amount_display.text.toString().toFloat() < 100 || amount_display.text.toString().toFloat() > 4000) {
                amt_input_err.visibility = View.VISIBLE
                amt_input_err.text = "Please deposit between 100 - 4000"
            } else {

                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)
                val builder = AlertDialog.Builder(context)
                builder.setView(dialogView)
                val dialog = builder.show()
                dialogView.instruction_hint.text = resources.getText(R.string.instruction_hint)
                dialogView.confirm.setOnClickListener {
                    dialog.dismiss()
                    amt_input_err.visibility = View.GONE
                    val client = OkHttpClient()
                    val formBody = FormBody.Builder()
                        .add("amount", amount_display.text.toString())
                        .add("userid", pk)
                        .add("currency", "0")
                        .add("PayWay", "42")
                        .add("method", "47")
                        .build()

                    val request = Request.Builder()
                        .url(URLs.ASIAPAY)
                        .post(formBody)
                        .build()
                    val response = client.newCall(request).execute()
                    if (response.code() != 200) {
                        MyAccount.info = "fail"
                        val res = Intent(context, MyAccount::class.java)
                        startActivity(res)
                    } else {
                        var unionData = response.body()!!.string()
                        //println(quickData)
                        orderId = JSONObject(unionData).getString("oid")
                        var union_url = JSONObject(unionData).getString("qr")

                        // startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(quickpay_url)))


                        val res = Intent(activity, AsiaUnionOpenPage::class.java)
                        res.putExtra("unionurl", union_url)
                        res.putExtra("unionorderId", orderId)
                        res.putExtra("unionbalance", amount_display.text.toString())
                        startActivity(res)

                    }

                }
            }
        }
    }
}
    /*: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_amount_input)
        change_method.setOnClickListener {
            startActivity(Intent(this, Deposit::class.java))
        }
    }

} */