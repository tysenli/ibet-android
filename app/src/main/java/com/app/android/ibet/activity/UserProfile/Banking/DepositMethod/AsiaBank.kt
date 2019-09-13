package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.api.Api
import com.app.android.ibet.fragment.CustomDropDownAdapter
import kotlinx.android.synthetic.main.frag_asiabank.*
import kotlinx.android.synthetic.main.frag_asiabank.amt_input_err
import kotlinx.android.synthetic.main.frag_asiabank.deposit_amount2
import kotlinx.android.synthetic.main.frag_asiabank.money_100
import kotlinx.android.synthetic.main.frag_asiabank.money_25
import kotlinx.android.synthetic.main.frag_asiabank.money_250
import kotlinx.android.synthetic.main.frag_asiabank.money_50

import kotlinx.android.synthetic.main.frag_display.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.*

class AsiaBank : Fragment() {
    //private var parentContext = context
    var userData = Api().get(BuildConfig.USER)
    var orderId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_asiabank, container, false)
    }

    override fun onStart() {
        super.onStart()
        var bankImg = arrayOf(R.drawable.guangfabank, R.drawable.bank_of_china, R.drawable.industrial, R.drawable.china_merchant, R.drawable.china_construction_bank_logo)
        //val arrayAdapter = CustomDropDownAdapter(context!!, bankImg)
        val bank = arrayOf("Choose Bank","广发银行","中国银行","中国工商银行","招商银行","中国建设银行")
        var bankId = 1
        deposit_amount2.hint = " Deposit 100 - 10,000"
        amt_input_err.visibility = View.GONE
        money_25.text = "100"
        money_50.text = "1000"
        money_100.text = "5000"
        money_250.text = "10000"
        val arrayAdapter = ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item, bank)
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        //CusAdapter(this, flag, language)
        bank_choose.adapter = arrayAdapter
       // bank_choose.prompt = "Choose Bank"
        bank_choose.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position != 0) {
                    bankId = position
                }
                //Toast.makeText(this, getString(R.string.selected_item) + " " + gender[position], Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
        var pk = JSONObject(userData).getString("pk")
        //println(pk)
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
            val client = OkHttpClient()
            val formBody = FormBody.Builder()
                .add("amount", amount_display.text.toString())
                .add("userid", pk)
                .add("currency", "0")
                .add("PayWay", "30")
                .add("method", bankId.toString())
                .build()

            val request = Request.Builder()
                .url(BuildConfig.ASIAPAY)
                .post(formBody)
                .build()
            val response = client.newCall(request).execute()

            if (response.code() != 200) {
                MyAccount.info = "fail"
                val res = Intent(context, MyAccount::class.java)
                startActivity(res)
            } else  {
                var bankData = response.body()!!.string()
                orderId = JSONObject(bankData).getString("order_id")
                var bankurl = JSONObject(bankData).getString("url") + "?cid=BRANDCQNGHUA3&oid=" + orderId

                val res = Intent(activity, AsiaBankOpenPage::class.java)

                res.putExtra("bankurl", bankurl)
                res.putExtra("bankorderId", orderId)
                res.putExtra("bankbalance", amount_display.text.toString())
                startActivity(res)

            }

        }
    }
}


class CustomDropDownAdapter(val context: Context, var image : Array<Int>) : BaseAdapter() {


    val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.custom_spinner_bank, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        // setting adapter item height programatically.

        val params = view.layoutParams
        params.height = 80
        view.layoutParams = params

        vh.img.setImageResource(image[position])
        return view
    }

    override fun getItem(position: Int): Any? {

        return null

    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getCount(): Int {
        return image.size
    }

    private class ItemRowHolder(row: View?) {
        val img : ImageView

        init {
            this.img = row?.findViewById(R.id.bank_img) as ImageView
        }
    }
}