package com.app.android.ibet.activity.UserProfile.Banking.WithdrawMethod

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount
import kotlinx.android.synthetic.main.frag_withdraw_password.*

class WithdrawPass : Fragment() {
    //private var parentContext = context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_withdraw_password, container, false)
    }

    override fun onStart() {
        super.onStart()
        pass_hint.visibility = View.GONE
        var gone = true
        pass.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (gone) {
                            pass_hint.visibility = View.VISIBLE
                            gone = false
                        } else {
                            pass_hint.visibility = View.GONE
                            gone = true
                        }

                    }

                }

                return v?.onTouchEvent(event) ?: true
            }
        })
        pass_cancel.setOnClickListener {
            MyAccount.info = "deposit"
            val intent = Intent(activity, MyAccount::class.java)
            startActivity(intent)
            activity!!.overridePendingTransition(0, 0)
        }


    }
}