package com.app.android.ibet.fragment

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filter.*
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.app.android.ibet.HomeGames
import com.app.android.ibet.R
import com.app.android.ibet.model.FilterModel
import com.app.android.ibet.model.GameModelResponse
import com.app.android.ibet.model.GameModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.game_list_item.view.*
import android.content.Context as ContentContext

class GameLobbyAdapter(private var games: ArrayList<GameModelResponse>): RecyclerView.Adapter<GameLobbyAdapter.GameViewHolder>() {

    private var filterListResult = ArrayList<GameModelResponse>()


    private var filtering = false



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GameViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.game_list_item, p0, false)
        return GameViewHolder(itemView)
    }


    override fun onBindViewHolder(p0: GameViewHolder, position: Int) {
        val game = if(filtering){
            filterListResult[position]
        }else{
            games[position]
        }

        val gameImg = game.fields.imageURL

        Picasso.with(p0.itemView.context).load(gameImg).into(p0.gameImg)

        p0.gameTitle.text =
            game.fields.name



    }
    override fun getItemCount(): Int {
        if(filtering){
            return filterListResult.size
        }
        return games.size
    }
    inner class GameViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var gameImg: ImageView = itemView.findViewById(R.id.game_img)
        var gameTitle: TextView = itemView.findViewById((R.id.game_title))
    }
    private fun clearFilter() {
        filtering = false
        games.clear()
    }

    fun updateGames(newGames : ArrayList<GameModelResponse>) {
        DiffUtil.calculateDiff(GameRowDiffCallback(newGames, games), false).dispatchUpdatesTo(this)
        clearFilter()
        games.addAll(newGames)
        notifyDataSetChanged()


    }

    class GameRowDiffCallback(private val newRows : List<GameModelResponse>, private val oldRows : List<GameModelResponse>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

            return oldItemPosition == newItemPosition
        }

        override fun getOldListSize(): Int = oldRows.size

        override fun getNewListSize(): Int = newRows.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldRow = oldRows[oldItemPosition]
            val newRow = newRows[newItemPosition]
            return oldRow == newRow
        }
    }
}