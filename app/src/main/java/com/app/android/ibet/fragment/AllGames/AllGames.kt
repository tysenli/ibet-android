package com.app.android.ibet.fragment.AllGames

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import com.app.android.ibet.R
import kotlinx.android.synthetic.main.fragment_all_games.*


class AllGames: Fragment(), FilterExpandableAdapter.OnFilterItemClick {


    private lateinit var filterListBody: ExpandableListView
    private lateinit var filterListAdapter: FilterExpandableAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_all_games, container, false)
        val thisContext = container!!.context
        filterListBody = v.findViewById(R.id.filter_List_parents)
        filterListAdapter = FilterExpandableAdapter(thisContext)
        filterListAdapter.setOnFilterItemClickListener(this)
        filterListBody.setAdapter(FilterExpandableAdapter(thisContext))
        filterListBody.setOnGroupClickListener(filterListAdapter)
        filterListBody.setOnChildClickListener(filterListAdapter)
        filterListAdapter.showItems(SampleFilter.getFilter())
        return v
    }

    override fun onStart() {
        super.onStart()
        filter_button.background = resources.getDrawable(R.color.colorPrimary)
        sort_button.background = resources.getDrawable(R.color.colorPrimary)
        filter_expan.visibility = View.GONE
        filter_button.setOnClickListener {
            filter_button.background = resources.getDrawable(R.color.brownish_grey)
            sort_button.background = resources.getDrawable(R.color.colorPrimary)
            filter_expan.visibility = View.VISIBLE

        }
        sort_button.setOnClickListener {
            filter_button.background = resources.getDrawable(R.color.colorPrimary)
            sort_button.background = resources.getDrawable(R.color.brownish_grey)
            filter_expan.visibility = View.GONE
        }

    }

    override fun onFilterClick(position: Int, menuItem: FilterListItem) {


    }

    override fun onSubFilterClick(position: Int, menuItem: FilterListItem) {

    }

}
