package com.app.android.ibet.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import com.app.android.ibet.R



class AllGames : Fragment() {
    private lateinit var filterListBody: ExpandableListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_all_games, container, false)
        val filterListBody = v.findViewById(R.id.filter_List_parents) as ExpandableListView
        filterListBody.setAdapter(FilterExpandableAdapter())
        return v
    }
    override fun onStart() {
        super.onStart()


    }

}
