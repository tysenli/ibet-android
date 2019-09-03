package com.app.android.ibet.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.MainActivity.Companion.isLogin
import kotlinx.android.synthetic.main.fragment_user_info.*

@SuppressLint("ValidFragment")
class UserInfo(context: Context): Fragment() {
    private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_info, container, false)

    }

    override fun onStart() {
        super.onStart()
        userlogout.setOnClickListener {
            isLogin = false

            startActivity(Intent(parentContext, MainActivity::class.java))
        }
    }
}