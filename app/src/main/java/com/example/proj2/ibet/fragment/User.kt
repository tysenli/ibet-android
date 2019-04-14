package com.example.proj2.ibet.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.proj2.ibet.Games
import com.example.proj2.ibet.R
import com.example.proj2.ibet.activity.Login
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*


@SuppressLint("ValidFragment")
class User (context: Context): Fragment() {

    private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_user, container, false)

    }

    override fun onStart() {
        super.onStart()
        usersignup.setOnClickListener { view ->
            //Log.d("btnSetup", "Selected")
            var intent = Intent(parentContext, Login::class.java)
            startActivity(intent)
        }

    }

}