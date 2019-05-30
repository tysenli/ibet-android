package com.app.android.ibet.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_language.*
import java.util.*




@SuppressLint("ValidFragment")
class Language (context: Context): Fragment() {


    private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_language, container, false)

    }

    override fun onStart() {
        super.onStart()
        chinese.setOnClickListener {
            chinese.setBackgroundColor(Color.LTGRAY)
            val locale = Locale("zh")
            val config = context!!.resources.configuration
            config.locale = locale
            context!!.resources.updateConfiguration(config, context!!.resources.displayMetrics)
            startActivity(Intent(parentContext, MainActivity::class.java))
        }
        english.setOnClickListener {
            english.setBackgroundColor(Color.LTGRAY)
            val locale = Locale("en")
            val config = context!!.resources.configuration
            config.locale = locale
            context!!.resources.updateConfiguration(config, context!!.resources.displayMetrics)
            startActivity(Intent(parentContext, MainActivity::class.java))
        }


    }

}