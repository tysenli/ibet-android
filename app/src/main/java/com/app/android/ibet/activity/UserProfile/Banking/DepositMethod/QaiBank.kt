package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.Banking.Deposit
import com.app.android.ibet.api.Api


import kotlinx.android.synthetic.main.frag_bt.*
import kotlinx.android.synthetic.main.frag_bt.amount_display
import kotlinx.android.synthetic.main.frag_bt.amt_input_err
import kotlinx.android.synthetic.main.frag_bt.btn_wechat_dep
import kotlinx.android.synthetic.main.frag_bt.depo_method_show
import kotlinx.android.synthetic.main.frag_bt.deposit_amount2
import kotlinx.android.synthetic.main.frag_bt.money_100
import kotlinx.android.synthetic.main.frag_bt.money_25
import kotlinx.android.synthetic.main.frag_bt.money_250
import kotlinx.android.synthetic.main.frag_bt.money_50
import kotlinx.android.synthetic.main.frag_help2pay.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class QaiBank : Fragment() {
    //private var parentContext = context
    var userData = Api().get(BuildConfig.USER)
    var orderId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_bt, container, false)
    }

    override fun onStart() {
        super.onStart()
        depo_method_show.background = resources.getDrawable(R.drawable.payment_bank)
        deposit_amount2.hint = " Deposit 100 - 10,000"
        amt_input_err.visibility = View.GONE
        var pk = JSONObject(userData).getString("pk")
        var curBank = ""
        money_25.text = "100"
        money_50.text = "1000"
        money_100.text = "5000"
        money_250.text = "10000"
        val bank = arrayOf("China UnionPay", "Agricultural Bank of China","Bank of East Asia","Bank of Beijing","Bank of China","Bank of Ningbo",
            "Bank Of Hebei","Bank of Shanghai","Beijing Rural Commercial Bank","Bank of Chengdu","China Bohai Bank","China Citic Bank",
            "China Merchants Bank","China Merchants Bank","Zhongshan Rural Credit Union","Yao Credit Cooperative Union","Bank of Communication",
            "Zhejiang Chouzhou commercial bank","China Everbright Bank","Industrial Bank Co Ltd","China Guangfa Bank","Guangzhou Rural Credit Cooperatives",
            "Bank of Guangzhou","GuangZhou Commercial Bank","Huishang Bank","Huaxia Bank","Hangzhou Bank","Industrial and Commercial Bank of China",
            "Jinshang Bank","China Minsheng Bank","Bank of Nanjing","Nanyang Commercial Bank","China Construction Bank","Postal Savings Bank of China",
            "Shunde Rural Commercial Bank","Shanghai Rural Commercial Bank","Shanghai Pudong Development Bank","Ping An Bank","Shenzhen Development Bank",
            "Bank of Tianjin","Hankou Bank","Bank of Wenzhou","China Zheshang Bank","Zhejiang Tailong Commercial Bank","People’s Bank of China",
            "HSBC","Bank of Dongguang")
        val bankvalue = arrayOf("OOO6CN","ABOCCN","BEASCN","BJCNCN","BKCHCN","BKNBCN","BKSHCN","BOSHCN","BRCBCN","CBOCCN","CHBHCN","CIBKCN","CMBCCN",
            "CN01CN","CN03CN","COMMCN","CZCBCN","EVSOCN","FJIBCN","GDBKCN","GNXSCN","GZCBCN","GZRCCN","HFCBCN","HXBKCN","HZCBCN","ICBKCN","JSHBCN",
            "MSBCCN","NJCBCN","NYCBCN","PCBCCN","PSBCCN","RCCSCN","SHRCCN","SPDBCN","SZCBCN","SZDBCN","TCCBCN","WHCBCN","WZCBCN","ZJCBCN","ZJTLCN",
            "PBOCCN","HSBCCN","DGCBCN")
        if (qai_bt_bankchoose != null) {
            val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,bank)

            qai_bt_bankchoose.adapter = arrayAdapter

            qai_bt_bankchoose.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    //Toast.makeText(context, getString(R.string.selected_item) + " " + currency[position], Toast.LENGTH_SHORT).show()
                    curBank = bankvalue[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }

        }
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
            if (amount_display.text.toString() == "" || amount_display.text.toString().toFloat() < 100 || amount_display.text.toString().toFloat() > 10000) {
                amt_input_err.visibility = View.VISIBLE
                amt_input_err.text = "Please deposit between 100 - 10000"
            } else {
                amt_input_err.visibility = View.GONE
                val client = OkHttpClient()
                val formBody = FormBody.Builder()
                    .add("amount", amount_display.text.toString())
                    .add("user_id", pk)
                    .add("currency", "0")
                    .add("language", "zh-Hans")
                    .add("method", "BANK_TRANSFER")
                    .add("bank", curBank)
                    .build()

                val request = Request.Builder()
                    .url(BuildConfig.QAICASH)
                    .post(formBody)
                    .build()
                val response = client.newCall(request).execute()
                if (response.code() != 200) {
                    MyAccount.info = "fail"
                    val res = Intent(context, MyAccount::class.java)
                    startActivity(res)
                } else {
                    val bankData = response.body()!!.string()
                    orderId = JSONObject(bankData).getJSONObject("paymentPageSession").getString("orderId")
                    var bank_url = JSONObject(bankData).getJSONObject("paymentPageSession").getString("paymentPageUrl")

                    val res = Intent(activity, QaiBankOpenPage::class.java)
                    res.putExtra("qai_bankurl", bank_url)
                    res.putExtra("qai_bankorderId", orderId)
                    res.putExtra("qai_bankbalance", amount_display.text.toString())
                    startActivity(res)
                    // val url = JSONObject(bankData).getString("url")
                    //val orderId = JSONObject(bankData).getString("order_id")
                    //val openUrl =  "$url?cid=BRANDCQNGHUA3&oid=$orderId"
                    //startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(openUrl)))

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
    } */

}