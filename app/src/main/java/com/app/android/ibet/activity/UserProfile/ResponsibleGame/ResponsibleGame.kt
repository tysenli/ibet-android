package com.app.android.ibet.activity.UserProfile.ResponsibleGame

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.facebook.FacebookSdk.getApplicationContext



class ResponsibleGame : Fragment() {
    //private var parentContext = context
    //var userData = Api().get(BuildConfig.USER)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_responsible_game, container, false)
    }

    override fun onStart() {
        super.onStart()
        lock_error.visibility = View.GONE
        remove_depo.visibility = View.GONE
        remove_loss.visibility = View.GONE
        daily_depo.setOnClickListener{
            daily_depo.background = resources.getDrawable(R.color.btn_d)
            weekly_depo.background = resources.getDrawable(R.color.btn_l)
            monthly_depo.background = resources.getDrawable(R.color.btn_l)

        }
        weekly_depo.setOnClickListener{
            daily_depo.background = resources.getDrawable(R.color.btn_l)
            weekly_depo.background = resources.getDrawable(R.color.btn_d)
            monthly_depo.background = resources.getDrawable(R.color.btn_l)
        }
        monthly_depo.setOnClickListener{
            daily_depo.background = resources.getDrawable(R.color.btn_l)
            weekly_depo.background = resources.getDrawable(R.color.btn_l)
            monthly_depo.background = resources.getDrawable(R.color.btn_d)
        }
        minus_depo.setOnClickListener {
            var amt = depo_limit_amt.text.toString().toInt()
            amt--
            depo_limit_amt.setText("$amt")
        }
        add_depo.setOnClickListener {
            var amt = depo_limit_amt.text.toString().toInt()
            amt++
            depo_limit_amt.setText("$amt")
        }
        save_depo_limit.setOnClickListener {
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
            loss_daily.background = resources.getDrawable(R.color.btn_d)
            loss_weekly.background = resources.getDrawable(R.color.btn_l)
            loss_monthly.background = resources.getDrawable(R.color.btn_l)

        }
        loss_weekly.setOnClickListener{
            loss_daily.background = resources.getDrawable(R.color.btn_l)
            loss_weekly.background = resources.getDrawable(R.color.btn_d)
            loss_monthly.background = resources.getDrawable(R.color.btn_l)
        }
        loss_monthly.setOnClickListener{
            loss_daily.background = resources.getDrawable(R.color.btn_l)
            loss_weekly.background = resources.getDrawable(R.color.btn_l)
            loss_monthly.background = resources.getDrawable(R.color.btn_d)
        }
        minus_loss.setOnClickListener {
            var amt = loss_limit_amt.text.toString().toInt()
            amt--
            loss_limit_amt.setText("$amt")
        }
        add_loss.setOnClickListener {
            var amt = loss_limit_amt.text.toString().toInt()
            amt++
            loss_limit_amt.setText("$amt")
        }
        save_loss_limit.setOnClickListener {
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