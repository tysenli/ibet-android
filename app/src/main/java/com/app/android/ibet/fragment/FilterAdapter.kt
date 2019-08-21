package com.app.android.ibet.fragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.android.ibet.R
import com.app.android.ibet.model.FilterModel
import kotlinx.android.synthetic.main.game_lobby_fragment.*

class FilterAdapter(private var elements: ArrayList<FilterModel>): RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    val fragment = GameLobbyAll()


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FilterAdapter.FilterViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.game_filter_item, p0, false)
        return FilterViewHolder(itemView)
    }
    override fun onBindViewHolder(p0: FilterViewHolder, position: Int) {
        val fragment = GameLobbyAll()
        val element = elements[position]

        var ele = ArrayList<String>()

        for(i in element.data){
            ele.add(i)
        }
        ele.add(0, element.name)

        var arrayAdapter = ArrayAdapter(p0.spinner.context, android.R.layout.simple_spinner_dropdown_item, ele)
        p0.spinner.adapter = arrayAdapter
        p0.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                println("HAHAHAHA")
                val filter = if(position > 0) {
                    element.data[position - 1]
                }else {
                    element.data[position]
                }
                println(filter)

                fragment.fetchGames(filter, "","","", "", "")



            }
        }

    }
    override fun getItemCount(): Int {

        return elements.size
    }
    inner class FilterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        //        var eleName: TextView = itemView.findViewById(R.id.game_label)
        var spinner: Spinner = itemView.findViewById((R.id.game_spinner))

    }







}