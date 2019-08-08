package com.app.android.ibet.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.model.GameModelResponse
import kotlinx.android.synthetic.main.game_lobby_fragment.*
import com.google.gson.reflect.TypeToken

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class GameLobbyAll : Fragment() {

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
        fetchGames()
    }



    private  fun fetchGames(){
        val url = BuildConfig.GAME_URL+ "live-casino"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call?, response: okhttp3.Response?) {
                val body = response?.body()?.string()
                print(body)
                Log.e("Success", body)

                val gson = GsonBuilder().create()

                val gameModelResponse: List<GameModelResponse> = gson.fromJson(body, object : TypeToken<List<GameModelResponse>>() { }.type)


                this@GameLobbyAll.activity!!.runOnUiThread {
                    game_recycler_list.adapter = GameLobbyAdapter(gameModelResponse)

                }
            }

            override fun onFailure(call: okhttp3.Call?, e: IOException?) {
                Log.e("TAG", "onFailure: "+e.toString() );
            }
        })
    }
//    private fun fetchGames() {
//        refreshLayout.isRefreshing = true
//        val type = "live-casino"
//
//
//        GameAPI().run {
//
//            getGames(type).enqueue(object :Callback<List<GameModelResponse>>{
//                override fun onFailure(call: Call<List<GameModelResponse>>, t: Throwable) {
//                    refreshLayout.isRefreshing = false
//                    Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show();
//                    Log.e("TAG", "onFailure: "+t.toString() );
//                }
//
//
//                override fun onResponse(call: Call<List<GameModelResponse>>, response: Response<List<GameModelResponse>>) {
//                    refreshLayout.isRefreshing = false
//
//
//
//                    if (response.isSuccessful()){
//                        Log.e("Success", Gson().toJson(response.body()))
//                        var games = response.body()!!
//
//                        games?.let{
//                            showGames(games)
//                        }
//
//                    }else{
//                        Log.e("unSuccess", Gson().toJson(response.errorBody()))
//                    }
//
//                }
//
//            })
//        }
//
//
//    }

//    private fun showGames(games: List<GameModelResponse>) {
//        println(games)
//
//        game_recycler_list.layoutManager = LinearLayoutManager(activity)
//        game_recycler_list.adapter = GameLobbyAdapter(games)
//
//    }
}

