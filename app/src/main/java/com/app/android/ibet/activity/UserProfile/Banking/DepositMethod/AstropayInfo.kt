package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.Banking.Deposit
import kotlinx.android.synthetic.main.activity_amount_input.*
import kotlinx.android.synthetic.main.frag_visa.*

class AstropayInfo: Fragment() {
    //private var parentContext = context
    companion object {
        var cardnum = ""
        var carddate = ""
        var cvv = ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_visa, container, false)
    }

    override fun onStart() {
        super.onStart()
        name_err.visibility = View.GONE
        num_err.visibility = View.GONE
        date_err.visibility = View.GONE
        code_err.visibility =  View.GONE

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

        }

    }

}
