package com.app.android.ibet.fragment

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
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
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import kotlinx.android.synthetic.main.game_lobby_fragment.*

class FilterAdapter(private var elements: ArrayList<FilterModel>, pp: Int): RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    val fragment = GameLobbyAll()
    val fragmentSlot = GameLobbySlots()
    companion object {
        lateinit var recycler: RecyclerView
    }
    val fragPosition = pp

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
//        val arguments  = fragment.arguments
//        val fragPosition = FragmentPagerItem.getPosition(arguments) - 1

        if (ele[0] == "Games Category"){
            p0.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

//                val game = view?.findViewById<RecyclerView>(R.id.game_recycler_list)

                    var filter = ""
                    if(position > 0) {
                        filter = element.data[position - 1]
                        fragment.fetchGames(fragPosition, recycler, filter, "", "", "", "", "")
                        fragmentSlot.fetchGames(fragPosition, recycler, filter, "", "", "", "", "")
                    }else if(position == 0){
                        fragment.fetchGames(fragPosition, recycler, "", "", "", "", "", "")
                    }

                }
            }
        }
        if (ele[0] == "Jackpot"){
            p0.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

//                val game = view?.findViewById<RecyclerView>(R.id.game_recycler_list)

                    var filter = ""
                    if(position > 0) {

                        filter = element.data[position - 1]
                        fragment.fetchGames(fragPosition, recycler, "", filter, "", "", "", "")
                        fragmentSlot.fetchGames(fragPosition, recycler, "", filter, "", "", "", "")
                    }else if(position == 0){
                        fragment.fetchGames(fragPosition, recycler, "", "", "", "", "", "")
                    }

                }
            }
        }
        if (ele[0] == "Provider"){
            p0.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

//                val game = view?.findViewById<RecyclerView>(R.id.game_recycler_list)

                    var filter = ""
                    if(position > 0) {

                        filter = element.data[position - 1]
                        fragment.fetchGames(fragPosition, recycler, "", "", filter, "", "", "")
                        fragmentSlot.fetchGames(fragPosition, recycler, "", "", filter, "", "", "")
                    }else if(position == 0){
                        fragment.fetchGames(fragPosition, recycler, "", "", "", "", "", "")
                    }

                }
            }
        }
        if (ele[0] == "Feature"){
            p0.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

//                val game = view?.findViewById<RecyclerView>(R.id.game_recycler_list)

                    var filter = ""
                    if(position > 0) {

                        filter = element.data[position - 1]
                        fragment.fetchGames(fragPosition, recycler, "", "", "", filter, "", "")
                        fragmentSlot.fetchGames(fragPosition, recycler, "", "", "", filter, "", "")
                    }else if(position == 0){
                        fragment.fetchGames(fragPosition, recycler, "", "", "", "", "", "")
                    }

                }
            }
        }
        if (ele[0] == "Theme"){
            p0.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

//                val game = view?.findViewById<RecyclerView>(R.id.game_recycler_list)

                    var filter = ""
                    if(position > 0) {
                        filter = element.data[position - 1]
                        fragment.fetchGames(fragPosition, recycler, "", "", "", "", filter, "")
                        fragmentSlot.fetchGames(fragPosition, recycler, "", "", "", "", filter, "")
                    }else if(position == 0){
                        fragment.fetchGames(fragPosition, recycler, "", "", "", "", "", "")
                    }

                }
            }
        }
        if (ele[0] == "Sort by"){
            p0.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

//                val game = view?.findViewById<RecyclerView>(R.id.game_recycler_list)

                    var filter = ""
                    if(position > 0) {
                        filter = element.data[position - 1]
                        fragment.fetchGames(fragPosition, recycler, "", "", "", "", "", filter)
                        //fragmentSlot.fetchGames(fragPosition, recycler, "", "", "", "", "", filter)
                    }else if(position == 0){
                        fragment.fetchGames(fragPosition, recycler, "", "", "", "", "", "")
                    }

                }
            }
        }


    }
    override fun getItemCount(): Int {

        return elements.size
    }
    class FilterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        //        var eleName: TextView = itemView.findViewById(R.id.game_label)
        var spinner: Spinner = itemView.findViewById((R.id.game_spinner))


    }

}