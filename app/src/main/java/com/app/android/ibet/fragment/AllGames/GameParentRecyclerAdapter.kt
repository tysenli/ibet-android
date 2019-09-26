package com.app.android.ibet.fragment.AllGames

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.adapter
import com.app.android.ibet.fragment.NewGames.NewGames
import com.app.android.ibet.fragment.NewGames.PopularGames
import com.app.android.ibet.fragment.NewGames.SlotsGames
import com.app.android.ibet.fragment.NewGames.TableGames
import com.app.android.ibet.model.FilterModel
import com.app.android.ibet.model.GameModelResponse
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_email_auth_p1.view.*
import kotlinx.android.synthetic.main.fragment_all_games.view.*
import kotlinx.android.synthetic.main.game_parent_recycler.*
import kotlinx.android.synthetic.main.game_parent_recycler.view.*
import kotlinx.android.synthetic.main.game_parent_recycler.view.game_child_recycler
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import android.R.id
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.coroutines.coroutineContext


class GameParentRecyclerAdapter(val categories: List<FilterModel>): RecyclerView.Adapter<GameParentRecyclerAdapter.ViewHolder>(){
    private val viewPool = RecyclerView.RecycledViewPool()
    private lateinit var gameModelResponse: List<GameModelResponse>
    private lateinit var childLayoutManager: LinearLayoutManager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_parent_recycler, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categories[0].data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[0]

        childLayoutManager = LinearLayoutManager(holder.recyclerView.context, LinearLayoutManager.HORIZONTAL, false)


        var childRecycler = holder.recyclerView
        childRecycler.layoutManager = childLayoutManager

        holder.recyclerView.setRecycledViewPool(viewPool)
        if(position < categories[0].data.size){
            holder.category_title.text = category.data[position]
            AllGames().fetchGames(category.data[position], holder.recyclerView, childLayoutManager, holder.progressBar, holder.viewAllButton)
        }



       //AllGames().progressAction(holder.progressBar, holder.recyclerView, childLayoutManager)


    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val recyclerView : RecyclerView = itemView.game_child_recycler
        val category_title: TextView =  itemView.category_title
        val progressBar: ProgressBar = itemView.GameProgressBar
        val viewAllButton: Button = itemView.viewAllButton

    }


}




