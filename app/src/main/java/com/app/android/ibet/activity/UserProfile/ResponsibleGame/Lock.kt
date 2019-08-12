package com.app.android.ibet.activity.UserProfile.ResponsibleGame

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R

class Lock : Fragment() {
    //private var parentContext = context
    //var userData = Api().get(BuildConfig.USER)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_lock, container, false)
    }

    override fun onStart() {
        super.onStart()
    }
}