package com.app.android.ibet.fragment

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
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
import android.support.v7.widget.Toolbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.*
import android.view.MenuInflater
import android.widget.*
import com.app.android.ibet.model.FilterModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.game_filter_item.*
import okhttp3.Call
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject


class GameLobbyAll : Fragment() {


    var searchView: SearchView?=null
    val compoiteDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar!!.setTitle("Filter")
        return inflater.inflate(R.layout.game_lobby_fragment, container, false);

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
        val url = BuildConfig.GAME_URL+ "live-casino"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call?, response: okhttp3.Response?) {
                val body = response?.body()?.string()
                //print(body)
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

    private fun fetchFilter(){

//        val LabelText = game_label
//        val spinner = game_spinner
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
                this@GameLobbyAll.activity!!.runOnUiThread {
                    filter_recycler_list.adapter = FilterAdapter(filterModel)
                }
//                for(i in 0..(body.length() - 1)){
//                    val name = body.getJSONObject(i).getString("name").toString()
//                    println(name)
//                    val data = body.getJSONObject(i).getString("data").toString()
//                    println(data)
//                    val dataArray = data.replace("\"", " ").replace("[", "").replace("]", "").split(",").toTypedArray()
//
//                    println(dataArray)
//                    filterMap.put(name, dataArray)
//                    this@GameLobbyAll.activity!!.runOnUiThread {
//                        LabelText.text = name
//
//                        if(spinner != null){
//                            var arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, dataArray)
//                            spinner.adapter = arrayAdapter
//                            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//                                override fun onNothingSelected(parent: AdapterView<*>?) {
//
//                                }
//
//                                override fun onItemSelected(
//                                    parent: AdapterView<*>?,
//                                    view: View?,
//                                    position: Int,
//                                    id: Long
//                                ) {
//
//                                }
//
//                            }
//                        }
//                    }
//
//
//
//                }



            }

        }))
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

