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
import kotlinx.android.synthetic.main.activity_amount_input_withdraw.*
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
    var lockInterval = 0
    var remindTime = 0
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

        var dailyDepoAmt: String? = "null"
        var weeklyDepoAmt: String? = "null"
        var monthlyDepoAmt: String? = "null"
        var dailyLossAmt: String? = "null"
        var weeklyLossAmt: String? = "null"
        var monthlyLossAmt: String? = "null"

        var dailyDepoExpiretime = ""
        var weeklyDepoExpiretime = ""
        var monthlyDepoExpiretime = ""
        var dailyLossExpiretime = ""
        var weeklyLossExpiretime = ""
        var monthlyLossExpiretime = ""

        if (response.code() == 200) {
            val gameData = response.body()!!.string()
            var depoArray = JSONObject(gameData).getJSONArray("deposit")
            //Log.e("depoArray",depoArray.toString() )
            for (i in 0 until depoArray.length()) {
                when (JSONObject(depoArray.get(i).toString()).get("intervalValue")) {
                    0 -> {
                        dailyDepoAmt = JSONObject(depoArray.get(i).toString()).get("amount").toString()
                        dailyDepoExpiretime = JSONObject(depoArray.get(i).toString()).get("expiration_time").toString()
                    }
                    1 -> {
                        weeklyDepoAmt = JSONObject(depoArray.get(i).toString()).get("amount").toString()
                        weeklyDepoExpiretime = JSONObject(depoArray.get(i).toString()).get("expiration_time").toString()
                    }
                    2 -> {
                        monthlyDepoAmt = JSONObject(depoArray.get(i).toString()).get("amount").toString()
                        monthlyDepoExpiretime =
                            JSONObject(depoArray.get(i).toString()).get("expiration_time").toString()
                    }
                }

            }

            var lossArray = JSONObject(gameData).getJSONArray("loss")
            for (i in 0 until lossArray.length()) {
                when (JSONObject(lossArray.get(i).toString()).get("intervalValue")) {
                    0 -> {
                        dailyLossAmt = JSONObject(lossArray.get(i).toString()).get("amount").toString()
                        dailyLossExpiretime = JSONObject(lossArray.get(i).toString()).get("expiration_time").toString()

                    }
                    1 -> {
                        weeklyLossAmt = JSONObject(lossArray.get(i).toString()).get("amount").toString()
                        weeklyLossExpiretime = JSONObject(lossArray.get(i).toString()).get("expiration_time").toString()
                    }
                    2 -> {
                        monthlyLossAmt = JSONObject(lossArray.get(i).toString()).get("amount").toString()
                        monthlyLossExpiretime =
                            JSONObject(lossArray.get(i).toString()).get("expiration_time").toString()
                    }
                }
            }
        }


        depo_limit_amt.hint = if (dailyDepoAmt == "null") "0" else dailyDepoAmt
        depo_cur.text = "Current Limit    \$0 out of \$${depo_limit_amt.hint}"
        depo_expire.text = "Limit expires on    \$0 out of \$$dailyDepoAmt"
        if (dailyDepoExpiretime.isEmpty()) {
            depo_show.visibility = View.VISIBLE
            depo_remove.visibility = View.GONE
        } else {
            depo_show.visibility = View.GONE
            depo_remove.visibility = View.VISIBLE
            depo_deletetime.text = dailyDepoExpiretime.substring(0,16)
        }
        //Log.e("amt",JSONObject(JSONObject(gameData).getJSONArray("deposit").get(2).toString()).getString("amount"))
        lock_error.visibility = View.GONE
        remove_depo.visibility = if (dailyDepoAmt == "null") View.GONE else View.VISIBLE
        remove_loss.visibility = if (dailyLossAmt == "null") View.GONE else View.VISIBLE

        daily_depo.setOnClickListener{
            depoInterval = 0
            depo_limit_amt.hint = if (dailyDepoAmt == "null") "0" else dailyDepoAmt
            depo_cur.text = "Current Limit    \$0 out of \$${depo_limit_amt.hint}"
            depo_expire.text = "Limit expires on    \$0 out of \$$dailyDepoAmt"
            remove_depo.visibility = if (dailyDepoAmt == "null") View.GONE else View.VISIBLE
            if (dailyDepoExpiretime.isEmpty()) {
                depo_show.visibility = View.VISIBLE
                depo_remove.visibility = View.GONE

            } else {
                depo_show.visibility = View.GONE
                depo_remove.visibility = View.VISIBLE

                depo_deletetime.text = dailyDepoExpiretime.substring(0,16)

            }
            daily_depo.background = resources.getDrawable(R.color.btn_d)
            weekly_depo.background = resources.getDrawable(R.color.btn_l)
            monthly_depo.background = resources.getDrawable(R.color.btn_l)

        }
        weekly_depo.setOnClickListener{
            depoInterval = 1
            depo_limit_amt.hint = if (weeklyDepoAmt == "null") "0" else weeklyDepoAmt
            depo_cur.text = "Current Limit    \$0 out of \$${depo_limit_amt.hint}"
            depo_expire.text = "Limit expires on    \$0 out of \$$weeklyDepoAmt"
            remove_depo.visibility = if (weeklyDepoAmt == "null") View.GONE else View.VISIBLE
            if (weeklyDepoExpiretime.isEmpty()) {
                depo_show.visibility = View.VISIBLE
                depo_remove.visibility = View.GONE
            } else {
                depo_show.visibility = View.GONE
                depo_remove.visibility = View.VISIBLE
                depo_deletetime.text = weeklyDepoExpiretime.substring(0,16)

            }
            daily_depo.background = resources.getDrawable(R.color.btn_l)
            weekly_depo.background = resources.getDrawable(R.color.btn_d)
            monthly_depo.background = resources.getDrawable(R.color.btn_l)
        }
        monthly_depo.setOnClickListener{
            depoInterval = 2
            remove_depo.visibility = if (monthlyDepoAmt == "null") View.GONE else View.VISIBLE
            depo_limit_amt.hint = if (monthlyDepoAmt == "null") "0" else monthlyDepoAmt
            depo_cur.text = "Current Limit    \$0 out of \$${depo_limit_amt.hint}"
            depo_expire.text = "Limit expires on    \$0 out of \$$monthlyDepoAmt"

            if (monthlyDepoExpiretime.isEmpty()) {
                depo_show.visibility = View.VISIBLE
                depo_remove.visibility = View.GONE
            } else {
                depo_show.visibility = View.GONE
                depo_remove.visibility = View.VISIBLE
                depo_deletetime.text = monthlyDepoExpiretime.substring(0,16)

            }
            daily_depo.background = resources.getDrawable(R.color.btn_l)
            weekly_depo.background = resources.getDrawable(R.color.btn_l)
            monthly_depo.background = resources.getDrawable(R.color.btn_d)
        }
        minus_depo.setOnClickListener {
            if (depo_limit_amt.text.isNotEmpty()) {
                var amt = depo_limit_amt.text.toString().toInt()
                if (amt > 0) {
                    amt--
                }
                depo_limit_amt.setText("$amt")
            }
        }
        add_depo.setOnClickListener {
            if (depo_limit_amt.text.isNotEmpty()) {
                var amt = depo_limit_amt.text.toString().toInt()
                amt++
                depo_limit_amt.setText("$amt")
            }
        }
        save_depo_limit.setOnClickListener {

            val client = OkHttpClient()

            val depoJson = JSONObject()
            val depoLimit = JSONArray()
            val inter = JSONArray()
            if (dailyDepoAmt != "null") {
                if (depoInterval != 0) {
                    depoLimit.put(dailyDepoAmt!!.toInt())
                    inter.put(0)
                }
            }
            if (weeklyDepoAmt != "null") {
                if (depoInterval != 1) {
                    depoLimit.put(weeklyDepoAmt!!.toInt())
                    inter.put(1)
                }
            }
            if (monthlyDepoAmt != "null") {
                if (depoInterval != 2) {
                    depoLimit.put(monthlyDepoAmt!!.toInt())
                    inter.put(2)
                }
            }
            if (depo_limit_amt.text.toString().isNotEmpty()) {
                depoLimit.put(depo_limit_amt.text.toString().toInt())
                inter.put(depoInterval)
                when (depoInterval) {
                    0 -> dailyDepoAmt = depo_limit_amt.text.toString()
                    1 -> weeklyDepoAmt = depo_limit_amt.text.toString()
                    2 -> monthlyDepoAmt = depo_limit_amt.text.toString()
                }
            }

            val JSON = MediaType.get("application/json; charset=utf-8")
            //Log.e("limit",depoLimit.toString())
            //Log.e("interval",inter.toString())
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
            //Log.e("code",response.code().toString())
            //Log.e("depolimit",response.body()!!.string())

            remove_depo.visibility = View.VISIBLE
            if (depo_limit_amt.text.toString().isNotEmpty()) {
                depo_cur.text = "Current Limit    \$0 out of \$${depo_limit_amt.text}"
            }
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
        remove_depo.setOnClickListener {
            val client = OkHttpClient()
            val depoJson = JSONObject()

            val JSON = MediaType.get("application/json; charset=utf-8")
            depoJson.put("id", 0)
            depoJson.put("interval", depoInterval)
            depoJson.put("user_id", JSONObject(userData).getString("pk"))
            depoJson.put("type", "deposit")
            val body = RequestBody.create(JSON, depoJson.toString())
            val request = Request.Builder()
                .url(BuildConfig.REMOVELIMIT)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            val dateData = response.body()!!.string()
            if (depoInterval == 0) {
                dailyDepoExpiretime = JSONObject(dateData).getString("expire_time")
                depo_deletetime.text = dailyDepoExpiretime.substring(0,16)
                depo_expire.text = "Limit expires on    \$0 out of \$$dailyDepoAmt"

            } else if (depoInterval == 1) {
                weeklyDepoExpiretime = JSONObject(dateData).getString("expire_time")
                depo_deletetime.text = weeklyDepoExpiretime.substring(0,16)
                depo_expire.text = "Limit expires on    \$0 out of \$$weeklyDepoAmt"

            } else {
                monthlyDepoExpiretime = JSONObject(dateData).getString("expire_time")
                depo_deletetime.text = monthlyDepoExpiretime.substring(0,16)
                depo_expire.text = "Limit expires on    \$0 out of \$$monthlyDepoAmt"

            }

            depo_show.visibility = View.GONE
            depo_remove.visibility = View.VISIBLE

            val toast = Toast.makeText(context,
                "Your limit will be deleted in 24 hours", Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 260)
            val view = toast.view
            view.setBackgroundResource(R.color.toast)
            val text = view.findViewById<TextView>(android.R.id.message)
            text.setTextColor(Color.parseColor("#ffffff"))
            toast.show()

        }
        remove_loss.setOnClickListener {
            val client = OkHttpClient()
            val lossJson = JSONObject()

            val JSON = MediaType.get("application/json; charset=utf-8")
            lossJson.put("id", 0)
            lossJson.put("interval", lossInterval)
            lossJson.put("user_id", JSONObject(userData).getString("pk"))
            lossJson.put("type", "loss")
            val body = RequestBody.create(JSON, lossJson.toString())
            val request = Request.Builder()
                .url(BuildConfig.REMOVELIMIT)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            val dateData = response.body()!!.string()
            if (lossInterval == 0) {
                dailyLossExpiretime = JSONObject(dateData).getString("expire_time")
                loss_deletetime.text = dailyLossExpiretime.substring(0,16)
                loss_expire.text = "Limit expires on    \$0 out of \$$dailyLossAmt"

            } else if (lossInterval == 1) {
                weeklyLossExpiretime = JSONObject(dateData).getString("expire_time")
                loss_deletetime.text = weeklyLossExpiretime.substring(0,16)
                loss_expire.text = "Limit expires on    \$0 out of \$$weeklyLossAmt"

            } else {
                monthlyLossExpiretime = JSONObject(dateData).getString("expire_time")
                loss_deletetime.text = monthlyLossExpiretime.substring(0,16)
                loss_expire.text = "Limit expires on    \$0 out of \$$monthlyLossAmt"

            }
            loss_show.visibility = View.GONE
            loss_remove.visibility = View.VISIBLE
            val toast = Toast.makeText(context,
                "Your limit will be deleted in 24 hours", Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 260)
            val view = toast.view
            view.setBackgroundResource(R.color.toast)
            val text = view.findViewById<TextView>(android.R.id.message)
            text.setTextColor(Color.parseColor("#ffffff"))
            toast.show()
        }
        depo_limit_cancel.setOnClickListener {
            val client = OkHttpClient()
            val depoJson = JSONObject()

            val JSON = MediaType.get("application/json; charset=utf-8")
            depoJson.put("id", 0)
            depoJson.put("interval", depoInterval)
            depoJson.put("user_id", JSONObject(userData).getString("pk"))
            depoJson.put("type", "deposit")
            val body = RequestBody.create(JSON, depoJson.toString())
            val request = Request.Builder()
                .url(BuildConfig.CANCELLIMIT)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            depo_show.visibility = View.VISIBLE
            depo_remove.visibility = View.GONE
        }
        loss_limit_cancel.setOnClickListener {
            val client = OkHttpClient()
            val lossJson = JSONObject()

            val JSON = MediaType.get("application/json; charset=utf-8")
            lossJson.put("id", 0)
            lossJson.put("interval", lossInterval)
            lossJson.put("user_id", JSONObject(userData).getString("pk"))
            lossJson.put("type", "loss")
            val body = RequestBody.create(JSON, lossJson.toString())
            val request = Request.Builder()
                .url(BuildConfig.CANCELLIMIT)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
            loss_show.visibility = View.VISIBLE
            loss_remove.visibility = View.GONE
        }

        loss_limit_amt.hint = if (dailyLossAmt == "null") "0" else dailyLossAmt
        loss_cur.text = "Current Limit    \$0 out of \$${loss_limit_amt.hint}"
        loss_expire.text = "Limit expires on    \$0 out of \$$dailyDepoAmt"

        if (dailyLossExpiretime.isEmpty()) {
            loss_show.visibility = View.VISIBLE
            loss_remove.visibility = View.GONE
        } else {
            loss_show.visibility = View.GONE
            loss_remove.visibility = View.VISIBLE
            loss_deletetime.text = dailyLossExpiretime.substring(0,16)
        }
        loss_daily.setOnClickListener{
            lossInterval = 0
            loss_limit_amt.hint = if (dailyLossAmt == "null") "0" else dailyLossAmt
            loss_cur.text = "Current Limit    \$0 out of \$${loss_limit_amt.hint}"
            loss_expire.text = "Limit expires on    \$0 out of \$$dailyDepoAmt"
            remove_loss.visibility = if (dailyLossAmt == "null") View.GONE else View.VISIBLE
            if (dailyLossExpiretime.isEmpty()) {
                loss_show.visibility = View.VISIBLE
                loss_remove.visibility = View.GONE
            } else {
                loss_show.visibility = View.GONE
                loss_remove.visibility = View.VISIBLE
                loss_deletetime.text = dailyLossExpiretime.substring(0,16)
            }
            loss_daily.background = resources.getDrawable(R.color.btn_d)
            loss_weekly.background = resources.getDrawable(R.color.btn_l)
            loss_monthly.background = resources.getDrawable(R.color.btn_l)

        }
        loss_weekly.setOnClickListener{
            lossInterval = 1
            loss_limit_amt.hint = if (weeklyLossAmt == "null") "0" else weeklyLossAmt
            loss_cur.text = "Current Limit    \$0 out of \$${loss_limit_amt.hint}"
            loss_expire.text = "Limit expires on    \$0 out of \$$weeklyDepoAmt"

            remove_loss.visibility = if (weeklyLossAmt == "null") View.GONE else View.VISIBLE
            if (weeklyLossExpiretime.isEmpty()) {
                loss_show.visibility = View.VISIBLE
                loss_remove.visibility = View.GONE
            } else {
                loss_show.visibility = View.GONE
                loss_remove.visibility = View.VISIBLE
                loss_deletetime.text = weeklyLossExpiretime.substring(0,16)
            }
            loss_daily.background = resources.getDrawable(R.color.btn_l)
            loss_weekly.background = resources.getDrawable(R.color.btn_d)
            loss_monthly.background = resources.getDrawable(R.color.btn_l)
        }
        loss_monthly.setOnClickListener{
            lossInterval = 2
            loss_limit_amt.hint = if (monthlyLossAmt == "null") "0" else monthlyLossAmt
            loss_cur.text = "Current Limit    \$0 out of \$${loss_limit_amt.hint}"
            loss_expire.text = "Limit expires on    \$0 out of \$$monthlyDepoAmt"

            remove_loss.visibility = if (monthlyLossAmt == "null") View.GONE else View.VISIBLE
            if (monthlyLossExpiretime.isEmpty()) {
                loss_show.visibility = View.VISIBLE
                loss_remove.visibility = View.GONE
            } else {
                loss_show.visibility = View.GONE
                loss_remove.visibility = View.VISIBLE
                loss_deletetime.text = monthlyLossExpiretime.substring(0,16)
            }
            loss_daily.background = resources.getDrawable(R.color.btn_l)
            loss_weekly.background = resources.getDrawable(R.color.btn_l)
            loss_monthly.background = resources.getDrawable(R.color.btn_d)
        }
        minus_loss.setOnClickListener {
            if (loss_limit_amt.text.isNotEmpty()) {
                var amt = loss_limit_amt.text.toString().toInt()
                if (amt > 0) {
                    amt--
                }
                loss_limit_amt.setText("$amt")
            }
        }
        add_loss.setOnClickListener {
            if (loss_limit_amt.text.isNotEmpty()) {
                var amt = loss_limit_amt.text.toString().toInt()
                amt++
                loss_limit_amt.setText("$amt")
            }
        }
        save_loss_limit.setOnClickListener {

            val client = OkHttpClient()
            val lossJson = JSONObject()
            val lossLimit = JSONArray()
            val inter = JSONArray()
            if (dailyLossAmt != "null") {
                if (lossInterval != 0) {
                    lossLimit.put(dailyDepoAmt!!.toInt())
                    inter.put(0)
                }
            }
            if (weeklyLossAmt != "null") {
                if (lossInterval != 1) {
                    lossLimit.put(weeklyDepoAmt!!.toInt())
                    inter.put(1)
                }
            }
            if (monthlyLossAmt != "null") {
                if (lossInterval != 2) {
                    lossLimit.put(monthlyDepoAmt!!.toInt())
                    inter.put(2)
                }
            }
            if (loss_limit_amt.text.toString().isNotEmpty()) {
                lossLimit.put(loss_limit_amt.text.toString().toInt())
                inter.put(lossInterval)
                when (lossInterval) {
                    0 -> dailyLossAmt = loss_limit_amt.text.toString()
                    1 -> weeklyLossAmt = loss_limit_amt.text.toString()
                    2 -> monthlyLossAmt = loss_limit_amt.text.toString()
                }
            }

            val JSON = MediaType.get("application/json; charset=utf-8")
            lossJson.put("limit", lossLimit)
            lossJson.put("interval", inter)
            lossJson.put("user_id", JSONObject(userData).getString("pk"))
            lossJson.put("type", "loss")
            val body = RequestBody.create(JSON, lossJson.toString())
            val request = Request.Builder()
                .url(BuildConfig.SETLIMIT)
                .post(body)
                .build()
            val response = client.newCall(request).execute()
           // Log.e("losslimit",response.body()!!.string())
            remove_loss.visibility = View.VISIBLE
            if (loss_limit_amt.text.toString().isNotEmpty()) {
                loss_cur.text = "Current Limit    \$0 out of \$${loss_limit_amt.text}"
            }
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
        five_min.setOnClickListener {
            remindTime = 5
            five_min.background = resources.getDrawable(R.color.btn_d)
            thirty_min.background = resources.getDrawable(R.color.btn_l)
            sixty_min.background = resources.getDrawable(R.color.btn_l)
            two_hour.background = resources.getDrawable(R.color.btn_l)
        }
        thirty_min.setOnClickListener {
            remindTime = 30
            five_min.background = resources.getDrawable(R.color.btn_l)
            thirty_min.background = resources.getDrawable(R.color.btn_d)
            sixty_min.background = resources.getDrawable(R.color.btn_l)
            two_hour.background = resources.getDrawable(R.color.btn_l)
        }
        sixty_min.setOnClickListener {
            remindTime = 60
            five_min.background = resources.getDrawable(R.color.btn_l)
            thirty_min.background = resources.getDrawable(R.color.btn_l)
            sixty_min.background = resources.getDrawable(R.color.btn_d)
            two_hour.background = resources.getDrawable(R.color.btn_l)
        }
        two_hour.setOnClickListener {
            remindTime = 120
            five_min.background = resources.getDrawable(R.color.btn_l)
            thirty_min.background = resources.getDrawable(R.color.btn_l)
            sixty_min.background = resources.getDrawable(R.color.btn_l)
            two_hour.background = resources.getDrawable(R.color.btn_d)
        }

        one_day.setOnClickListener {
            lockInterval = 0
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
            lockInterval = 1
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
            lockInterval = 2
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
            lockInterval = 3
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
            lockInterval = 4
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
            lockInterval = 5
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
            lockInterval = 6
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
                var res = Intent(activity, MyAccount::class.java)
                startActivity(res)
                activity!!.overridePendingTransition(0, 0)
            } else {
                lock_error.visibility = View.VISIBLE
            }
        }
    }
}