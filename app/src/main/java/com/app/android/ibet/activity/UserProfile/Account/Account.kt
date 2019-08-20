package com.app.android.ibet.activity.UserProfile.Account

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.api.Api
import kotlinx.android.synthetic.main.frag_account.*

class Account : Fragment() {
    private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_account, container, false)
    }
    override fun onStart() {
        super.onStart()
        var userData = Api().get(BuildConfig.USER)
        //println(userData)

    }

}