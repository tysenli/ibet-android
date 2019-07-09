package com.app.android.ibet.activity.UserProfile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R

class Deposit : Fragment() {
    //private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_deposit, container, false)
    }

    override fun onStart() {
        super.onStart()
    }
}