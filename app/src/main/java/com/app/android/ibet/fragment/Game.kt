package com.app.android.ibet.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R


@SuppressLint("ValidFragment")

class Game : Fragment() {
    // private var parentContext = context
    private var initialized: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_game, container, false)
    }

    override fun onStart() {
        super.onStart()

    }
}