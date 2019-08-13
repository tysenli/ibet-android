package com.app.android.ibet.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.model.FilterModel
import com.app.android.ibet.model.GameModelResponse
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.game_lobby_fragment.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class GameLobbyBlackjack: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_lobby_fragment, container, false)
    }


    override fun onStart() {
        super.onStart()
        game_recycler_list.layoutManager = LinearLayoutManager(this.context)
        //game_recycler_list.adapter = GameLobbyAdapter(GameList)
        filter_recycler_list.layoutManager = GridLayoutManager(this.context, 2)
        fetchGames()
        fetchFilter()
    }



    private  fun fetchGames(){
        val url = BuildConfig.GAME_URL+ "live-casino" + BuildConfig.GAME_URL_CATEGORY + "blackjack"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call?, response: okhttp3.Response?) {
                val body = response?.body()?.string()
                print(body)
                Log.e("Success", body)

                val gson = GsonBuilder().create()

                val gameModelResponse: List<GameModelResponse> = gson.fromJson(body, object : TypeToken<List<GameModelResponse>>() { }.type)


                this@GameLobbyBlackjack.activity!!.runOnUiThread {
                    game_recycler_list.adapter = GameLobbyAdapter(gameModelResponse)

                }
            }

            override fun onFailure(call: okhttp3.Call?, e: IOException?) {
                Log.e("TAG", "onFailure: "+e.toString() );
            }
        })
    }
    private fun fetchFilter(){

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

                val filterMap = mutableMapOf<String, Array<String>>()
                val gson = GsonBuilder().create()
                val filterModel: List<FilterModel> = gson.fromJson(body, object :TypeToken<List<FilterModel>>() { }.type)
                this@GameLobbyBlackjack.activity!!.runOnUiThread {
                    filter_recycler_list.adapter = FilterAdapter(filterModel)
                }
            }

        }))
    }
}