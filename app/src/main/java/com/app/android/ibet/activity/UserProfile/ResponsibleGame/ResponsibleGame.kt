package com.app.android.ibet.activity.UserProfile.ResponsibleGame

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.frag_responsible_game.*
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.UserProfile.Banking.DepositMethod.AstropayInfo
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.userData
import com.facebook.FacebookSdk.getApplicationContext
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_amount_input.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject


class ResponsibleGame : Fragment() {
    //private var parentContext = context
    //var userData = Api().get(BuildConfig.USER)
    private var depoInterval = 0
    private var lossInterval = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_responsible_game, container, false)
    }

    override fun onStart() {
        super.onStart()
        val request = Request.Builder()
            .url(BuildConfig.GETLIMIT + JSONObject(userData).getString("pk"))
            .build()
        val response = OkHttpClient().newCall(request).execute()
        //Log.e("response",response.body()!!.string())
        val gameData = response.body()!!.string()
        var dailyDepoAmt = 0
        var weeklyDepoAmt = 0
        var monthlyDepoAmt = 0
        var dailyLossAmt = 0
        var weeklyLossAmt = 0
        var monthlyLossAmt = 0


        //depo_limit_amt.hint = JSONObject(JSONObject(gameData).getJSONArray("deposit").get(2).toString()).getString("amount")
        //Log.e("amt",JSONObject(JSONObject(gameData).getJSONArray("deposit").get(2).toString()).getString("amount"))
        lock_error.visibility = View.GONE
        remove_depo.visibility = View.GONE
        remove_loss.visibility = View.GONE
        daily_depo.setOnClickListener{
            depoInterval = 0
            //depo_limit_amt.hint = JSONObject(JSONObject(gameData).getJSONArray("deposit").get(2).toString()).getString("amount")
            daily_depo.background = resources.getDrawable(R.color.btn_d)
            weekly_depo.background = resources.getDrawable(R.color.btn_l)
            monthly_depo.background = resources.getDrawable(R.color.btn_l)

        }
        weekly_depo.setOnClickListener{
            depoInterval = 1
            //depo_limit_amt.hint = JSONObject(JSONObject(gameData).getJSONArray("deposit").get(0).toString()).getString("amount")
            daily_depo.background = resources.getDrawable(R.color.btn_l)
            weekly_depo.background = resources.getDrawable(R.color.btn_d)
            monthly_depo.background = resources.getDrawable(R.color.btn_l)
        }
        monthly_depo.setOnClickListener{
            depoInterval = 2
            //depo_limit_amt.hint = JSONObject(JSONObject(gameData).getJSONArray("deposit").get(1).toString()).getString("amount")
            daily_depo.background = resources.getDrawable(R.color.btn_l)
            weekly_depo.background = resources.getDrawable(R.color.btn_l)
            monthly_depo.background = resources.getDrawable(R.color.btn_d)
        }
        minus_depo.setOnClickListener {
            var amt = depo_limit_amt.text.toString().toInt()
            if (amt > 0) {
                amt--
            }
            depo_limit_amt.setText("$amt")
        }
        add_depo.setOnClickListener {
            var amt = depo_limit_amt.text.toString().toInt()
            amt++
            depo_limit_amt.setText("$amt")
        }
        save_depo_limit.setOnClickListener {
            val client = OkHttpClient()

            val depoJson = JSONObject()
            val depoLimit = JSONArray()
            val inter = JSONArray()
            depoLimit.put(depo_limit_amt.text.toString().toInt())
            inter.put(depoInterval)

            val JSON = MediaType.get("application/json; charset=utf-8")

            depoJson.put("limit", depoLimit)
            depoJson.put("interval", inter)
            depoJson.put("user_id", JSONObject(userData).getString("pk"))
            depoJson.put("type", "deposit")
            val body = RequestBody.create(JSON, depoJson.toString())
            val request = Request.Builder()
                .url(BuildConfig.SETLIMIT)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            Log.e("code",response.code().toString())
            Log.e("depolimit",response.body()!!.string())

            remove_depo.visibility = View.VISIBLE
            val toast = Toast.makeText(context,
                "Changes are successfully updated", Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 260)
            val view = toast.view
            view.setBackgroundResource(R.color.toast)
            val text = view.findViewById<TextView>(android.R.id.message)
            text.setTextColor(Color.parseColor("#ffffff"))
            toast.show()
        }

        loss_daily.setOnClickListener{
            lossInterval = 0
            loss_daily.background = resources.getDrawable(R.color.btn_d)
            loss_weekly.background = resources.getDrawable(R.color.btn_l)
            loss_monthly.background = resources.getDrawable(R.color.btn_l)

        }
        loss_weekly.setOnClickListener{
            lossInterval = 1
            loss_daily.background = resources.getDrawable(R.color.btn_l)
            loss_weekly.background = resources.getDrawable(R.color.btn_d)
            loss_monthly.background = resources.getDrawable(R.color.btn_l)
        }
        loss_monthly.setOnClickListener{
            lossInterval = 2
            loss_daily.background = resources.getDrawable(R.color.btn_l)
            loss_weekly.background = resources.getDrawable(R.color.btn_l)
            loss_monthly.background = resources.getDrawable(R.color.btn_d)
        }
        minus_loss.setOnClickListener {
            var amt = loss_limit_amt.text.toString().toInt()
            if (amt > 0) {
                amt--
            }
            loss_limit_amt.setText("$amt")
        }
        add_loss.setOnClickListener {
            var amt = loss_limit_amt.text.toString().toInt()
            amt++
            loss_limit_amt.setText("$amt")
        }
        save_loss_limit.setOnClickListener {
            val client = OkHttpClient()


            val depoJson = JSONObject()
            val lossLimit = JSONArray()
            val inter = JSONArray()
            lossLimit.put(loss_limit_amt.text.toString().toInt())
            inter.put(lossInterval)
            val JSON = MediaType.get("application/json; charset=utf-8")
            depoJson.put("limit", lossLimit)
            depoJson.put("interval", inter)
            depoJson.put("user_id", JSONObject(userData).getString("pk"))
            depoJson.put("type", "loss")
            val body = RequestBody.create(JSON, depoJson.toString())
            val request = Request.Builder()
                .url(BuildConfig.ASTROPAY)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            Log.e("losslimit",response.body()!!.string())
            remove_loss.visibility = View.VISIBLE
            val toast = Toast.makeText(context,
                "Changes are successfully updated", Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 260)
            val view = toast.view
            view.setBackgroundResource(R.color.toast)
            val text = view.findViewById<TextView>(android.R.id.message)
            text.setTextColor(Color.parseColor("#ffffff"))
            toast.show()
        }
        one_day.setOnClickListener {
            one_day.background = resources.getDrawable(R.color.btn_d)
            seven_day.background = resources.getDrawable(R.color.btn_l)
            thirty_day.background = resources.getDrawable(R.color.btn_l)
            six_month.background = resources.getDrawable(R.color.btn_l)
            one_year.background = resources.getDrawable(R.color.btn_l)
            three_year.background = resources.getDrawable(R.color.btn_l)
            five_year.background = resources.getDrawable(R.color.btn_l)
            indefinite.background = resources.getDrawable(R.color.btn_l)
        }
        seven_day.setOnClickListener {
            one_day.background = resources.getDrawable(R.color.btn_l)
            seven_day.background = resources.getDrawable(R.color.btn_d)
            thirty_day.background = resources.getDrawable(R.color.btn_l)
            six_month.background = resources.getDrawable(R.color.btn_l)
            one_year.background = resources.getDrawable(R.color.btn_l)
            three_year.background = resources.getDrawable(R.color.btn_l)
            five_year.background = resources.getDrawable(R.color.btn_l)
            indefinite.background = resources.getDrawable(R.color.btn_l)
        }
        thirty_day.setOnClickListener {
            one_day.background = resources.getDrawable(R.color.btn_l)
            seven_day.background = resources.getDrawable(R.color.btn_l)
            thirty_day.background = resources.getDrawable(R.color.btn_d)
            six_month.background = resources.getDrawable(R.color.btn_l)
            one_year.background = resources.getDrawable(R.color.btn_l)
            three_year.background = resources.getDrawable(R.color.btn_l)
            five_year.background = resources.getDrawable(R.color.btn_l)
            indefinite.background = resources.getDrawable(R.color.btn_l)
        }
        six_month.setOnClickListener {
            one_day.background = resources.getDrawable(R.color.btn_l)
            seven_day.background = resources.getDrawable(R.color.btn_l)
            thirty_day.background = resources.getDrawable(R.color.btn_l)
            six_month.background = resources.getDrawable(R.color.btn_d)
            one_year.background = resources.getDrawable(R.color.btn_l)
            three_year.background = resources.getDrawable(R.color.btn_l)
            five_year.background = resources.getDrawable(R.color.btn_l)
            indefinite.background = resources.getDrawable(R.color.btn_l)
        }
        one_year.setOnClickListener {
            one_day.background = resources.getDrawable(R.color.btn_l)
            seven_day.background = resources.getDrawable(R.color.btn_l)
            thirty_day.background = resources.getDrawable(R.color.btn_l)
            six_month.background = resources.getDrawable(R.color.btn_l)
            one_year.background = resources.getDrawable(R.color.btn_d)
            three_year.background = resources.getDrawable(R.color.btn_l)
            five_year.background = resources.getDrawable(R.color.btn_l)
            indefinite.background = resources.getDrawable(R.color.btn_l)
        }
        three_year.setOnClickListener {
            one_day.background = resources.getDrawable(R.color.btn_l)
            seven_day.background = resources.getDrawable(R.color.btn_l)
            thirty_day.background = resources.getDrawable(R.color.btn_l)
            six_month.background = resources.getDrawable(R.color.btn_l)
            one_year.background = resources.getDrawable(R.color.btn_l)
            three_year.background = resources.getDrawable(R.color.btn_d)
            five_year.background = resources.getDrawable(R.color.btn_l)
            indefinite.background = resources.getDrawable(R.color.btn_l)
        }
        five_year.setOnClickListener {
            one_day.background = resources.getDrawable(R.color.btn_l)
            seven_day.background = resources.getDrawable(R.color.btn_l)
            thirty_day.background = resources.getDrawable(R.color.btn_l)
            six_month.background = resources.getDrawable(R.color.btn_l)
            one_year.background = resources.getDrawable(R.color.btn_l)
            three_year.background = resources.getDrawable(R.color.btn_l)
            five_year.background = resources.getDrawable(R.color.btn_d)
            indefinite.background = resources.getDrawable(R.color.btn_l)
        }
        indefinite.setOnClickListener {
            one_day.background = resources.getDrawable(R.color.btn_l)
            seven_day.background = resources.getDrawable(R.color.btn_l)
            thirty_day.background = resources.getDrawable(R.color.btn_l)
            six_month.background = resources.getDrawable(R.color.btn_l)
            one_year.background = resources.getDrawable(R.color.btn_l)
            three_year.background = resources.getDrawable(R.color.btn_l)
            five_year.background = resources.getDrawable(R.color.btn_l)
            indefinite.background = resources.getDrawable(R.color.btn_d)
        }

        lock_account.setOnClickListener {
            if (check_lock.isChecked) {
                info = "lock_account"
                startActivity(Intent(activity, MyAccount::class.java))
                activity!!.overridePendingTransition(0, 0)
            } else {
                lock_error.visibility = View.VISIBLE
            }
        }
    }
}