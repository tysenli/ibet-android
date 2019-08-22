package com.app.android.ibet.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.model.FilterModel
import com.app.android.ibet.model.GameModelResponse
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import kotlinx.android.synthetic.main.game_lobby_fragment.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class GameLobbySlots: Fragment() {
    private var parentContext = context
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val position = FragmentPagerItem.getPosition(arguments) - 1

        val view = inflater.inflate(R.layout.game_lobby_fragment, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.game_recycler_list)
        recyclerView.layoutManager = LinearLayoutManager(parentContext)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        FilterAdapter.recycler = recyclerView
        fetchGames(position, recyclerView,"", "", "", "", "", "")

        val filterView = view.findViewById<RecyclerView>(R.id.filter_recycler_list)
        filterView.layoutManager = GridLayoutManager(parentContext, 2)
        fetchFilter(filterView, position)


        return view

    }


    override fun onStart() {
        super.onStart()

    }



    fun fetchGames(position: Int, gameRecycler: RecyclerView, filter: String, jackpot: String, provider: String, feature: String, theme: String, sort: String){
        var tabName = ""
        if(position >= 0){
            Log.e("position", (position).toString())

            when(position){
                0 -> tabName = "all"
            }
        }


        var url = BuildConfig.GAME_URL+ "slots" + BuildConfig.GAME_URL_CATEGORY + tabName

        if (filter != "") {
            url = url + BuildConfig.GAME_URL_FILTER + filter
        }
        if(jackpot != ""){
            url = url + BuildConfig.GAME_URL_JACKPOT + jackpot
        }
        if(provider != ""){
            url = url + BuildConfig.GAME_URL_PROVIDER + provider
        }
        if(feature != ""){
            url = url + BuildConfig.GAME_URL_FEATURE + feature
        }
        if(theme != ""){
            url = url + BuildConfig.GAME_URL_THEME + theme
        }
        if(sort != ""){
            url = url + BuildConfig.GAME_URL_SORT + sort
        }

        val request = Request.Builder().url(url).build()
        Log.e("request", request.toString())

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onResponse(call: Call?, response: Response?) {

                val body = response?.body()?.string()

                Log.e("Success", body)

                val gson = GsonBuilder().create()

                val gameModelResponse: ArrayList<GameModelResponse> =
                    gson.fromJson(body, object : TypeToken<ArrayList<GameModelResponse>>() {}.type)

//                    this@GameLobbyAll.activity?.runOnUiThread {


                val adapter = GameLobbyAdapter(gameModelResponse)
                Log.e("adapter", gameRecycler?.adapter.toString())
                //gameRecycler?.adapter = adapter

                gameRecycler.post(object : Runnable{
                    override fun run(){
                        if (gameRecycler?.adapter == null) {

                            gameRecycler?.adapter = adapter
                            adapter.notifyDataSetChanged()


                        } else {


                            Log.e("Array size", gameModelResponse.size.toString())
                            (gameRecycler.adapter as GameLobbyAdapter).updateGames(gameModelResponse)



                        }
                    }
                })

            }

            override fun onFailure(call: okhttp3.Call?, e: IOException?) {
                Log.e("TAG", "onFailure: "+e.toString() );
            }
        })


    }
    private fun fetchFilter(filterRecycler: RecyclerView, position: Int){

        val url = BuildConfig.GAME_FILTER
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue((object: okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("TAG", "onFailure: "+e.toString() );
            }

            override fun onResponse(call: Call, response: Response) {

                val body = response?.body()?.string()
//                Log.e("Success", body)

                val gson = GsonBuilder().create()
                val filterModel: ArrayList<FilterModel> = gson.fromJson(body, object :TypeToken<ArrayList<FilterModel>>() { }.type)
                filterRecycler?.post(object :Runnable{
                    override fun run() {
                        filterRecycler.adapter = FilterAdapter(filterModel, position + 1)
                        // fetchGames(,FilterAdapter.Filter, "", "", "", "", "")
                    }
                })

            }

        }))
    }
}