package com.app.android.ibet.fragment

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat.getSystemService
import androidx.viewpager.widget.ViewPager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
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
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.view.*
import android.view.MenuInflater
import android.widget.*
import com.app.android.ibet.model.FilterModel
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_casino.*
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

        val url = BuildConfig.GAME_URL+ "live-casino" + BuildConfig.GAME_URL_CATEGORY + tabName

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call?, response: okhttp3.Response?) {
                val body = response?.body()?.string()

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
            }

        }))
    }



}

