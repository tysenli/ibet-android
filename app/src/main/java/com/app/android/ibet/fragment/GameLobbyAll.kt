package com.app.android.ibet.fragment



import android.os.Bundle
import android.os.Handler

import android.util.Log
import com.app.android.ibet.R
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.model.GameModelResponse
import kotlinx.android.synthetic.main.game_lobby_fragment.*
import com.google.gson.reflect.TypeToken

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.android.ibet.ViewModel.GameViewModel
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.adapter

import com.app.android.ibet.model.FilterModel
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.game_list_item.view.*

import okhttp3.Call
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject


class GameLobbyAll : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.game_lobby_fragment, container, false)
        //val spinner = view.findViewById<Spinner>(R.id.filter_spinner)
        //setupSpinner(spinner)
        return view

    }


    override fun onStart() {
        super.onStart()

        game_recycler_list.layoutManager = LinearLayoutManager(this.context)
        game_recycler_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        filter_recycler_list.layoutManager = GridLayoutManager(this.context, 2)
        fetchGames("", "", "", "", "", "")

        fetchFilter()
    }

//    fun setupSpinner(spinner: Spinner) {
//        var filters = ArrayList<String>()
//        val url = BuildConfig.GAME_FILTER
//        val request = Request.Builder().url(url).build()
//        val client = OkHttpClient()
//        client.newCall(request).enqueue(object : okhttp3.Callback {
//            override fun onFailure(call: Call, e: IOException) {
//
//            }
//
//            override fun onResponse(call: Call?, response: Response?) {
//                if (response!!.isSuccessful()) {
//                    val body = response.body()?.string()
//                    val gson = GsonBuilder().create()
//                    val filterModel: ArrayList<FilterModel> =
//                        gson.fromJson(body, object : TypeToken<ArrayList<FilterModel>>() {}.type)
//                    for (i in filterModel) {
//                        filters.add(i.name)
//                    }
//                    filters.add(0, "Select a Filter")
//                    println(filters)
//                    activity?.runOnUiThread {
//                        var arrayAdapter =
//                            ArrayAdapter(spinner.context, android.R.layout.simple_spinner_dropdown_item, filters)
//                        spinner.adapter = arrayAdapter
//                        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//                            }
//
//                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//
//                                val filter = if (position > 0) {
//                                    filters[position - 1]
//                                } else {
//                                    filters[position]
//                                }
//
//                                //fetchGames(filter, "", "", "", "", "")
//
//
//                            }
//
//                        }
//                    }
//
//
//                }
//            }
//
//
//        })
//
//    }



    fun fetchGames( filter: String, jackpot: String, provider: String, feature: String, theme: String, sort: String){
        val position = FragmentPagerItem.getPosition(arguments)
        Log.e("position", position.toString())
        var tabName = ""
        when(position){
            0 -> tabName = "all"
            1 -> tabName = "roulette"
            2 -> tabName = "blackjack"
            3 -> tabName = "baccarat"
            4 -> tabName = "poker"
            5 -> tabName = "tournaments"
        }

        var url = BuildConfig.GAME_URL+ "live-casino" + BuildConfig.GAME_URL_CATEGORY + tabName
//        println(filter)
//
//        if (filter != "") {
//            url = url + BuildConfig.GAME_URL_FILTER + filter
//        }
//        if(jackpot != ""){
//            url = url + BuildConfig.GAME_URL_JACKPOT + jackpot
//        }
//        if(provider != ""){
//            url = url + BuildConfig.GAME_URL_PROVIDER + provider
//        }
//        if(feature != ""){
//            url = url + BuildConfig.GAME_URL_FEATURE + feature
//        }
//        if(theme != ""){
//            url = url + BuildConfig.GAME_URL_THEME + theme
//        }
//        if(sort != ""){
//            url = url + BuildConfig.GAME_URL_SORT + sort
//        }

        val request = Request.Builder().url(url).build()
        println(request)

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onResponse(call: Call?, response: Response?) {
                if (response!!.isSuccessful()) {
                    val body = response?.body()?.string()

                    Log.e("Success", body)

                    val gson = GsonBuilder().create()

                    val gameModelResponse: ArrayList<GameModelResponse> =
                        gson.fromJson(body, object : TypeToken<ArrayList<GameModelResponse>>() {}.type)


                    this@GameLobbyAll.activity?.runOnUiThread {

                        game_recycler_list.adapter = GameLobbyAdapter(gameModelResponse)

                    }
//                    gameModelResponse.clear()
//                    gameModelResponse.addAll(gson.fromJson(body, object : TypeToken<ArrayList<GameModelResponse>>() {}.type))
//                    (game_recycler_list.adapter as GameLobbyAdapter).notifyDataSetChanged()

                }



//                this@GameLobbyAll.activity?.runOnUiThread {
//
//                    game_recycler_list.adapter = GameLobbyAdapter(gameModelResponse)
//
//
//                }
            }

            override fun onFailure(call: okhttp3.Call?, e: IOException?) {
                Log.e("TAG", "onFailure: "+e.toString() );
            }
        })
    }

    fun fetchFilter(){

        val url = BuildConfig.GAME_FILTER
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue((object: okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("TAG", "onFailure: "+e.toString() );
            }

            override fun onResponse(call: Call, response: Response) {

                val body = response?.body()?.string()
                Log.e("Success", body)

                val gson = GsonBuilder().create()
                val filterModel: ArrayList<FilterModel> = gson.fromJson(body, object :TypeToken<ArrayList<FilterModel>>() { }.type)
                this@GameLobbyAll.activity?.runOnUiThread {

                    filter_recycler_list.adapter = FilterAdapter(filterModel)
                }
            }

        }))
    }





}