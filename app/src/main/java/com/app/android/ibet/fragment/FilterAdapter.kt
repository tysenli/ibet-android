package com.app.android.ibet.fragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.app.android.ibet.R
import com.app.android.ibet.model.FilterModel
import com.squareup.picasso.Picasso

class FilterAdapter(val elements: List<FilterModel>): RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FilterAdapter.FilterViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.game_filter_item, p0, false)
        return FilterViewHolder(itemView)
    }
    override fun onBindViewHolder(p0: FilterViewHolder, position: Int) {

        val element =  elements[position]

        p0.eleName.text =
            element.name

        var arrayAdapter = ArrayAdapter(p0.spinner.context, android.R.layout.simple_spinner_dropdown_item, element.data)
        p0.spinner.adapter = arrayAdapter
        p0.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                }

            }

    }
    override fun getItemCount(): Int {

        return elements.size
    }
    inner class FilterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var eleName: TextView = itemView.findViewById(R.id.game_label)
        var spinner: Spinner = itemView.findViewById((R.id.game_spinner))

    }


}