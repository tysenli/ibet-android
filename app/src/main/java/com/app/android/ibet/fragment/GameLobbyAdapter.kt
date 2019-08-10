package com.app.android.ibet.fragment

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
import com.app.android.ibet.model.GameModelResponse
import com.app.android.ibet.model.GameModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.game_list_item.view.*
import android.content.Context as ContentContext

class GameLobbyAdapter(val games: List<GameModelResponse>): RecyclerView.Adapter<GameLobbyAdapter.GameViewHolder>(),Filterable {

    internal var filterListResult: List<GameModelResponse>
    init {
        this.filterListResult = games
    }
    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(charString: CharSequence?): FilterResults {
                val charSearch = charString.toString()
                if(charSearch.isEmpty()){
                    filterListResult = games
                }else{
                    var resultList = ArrayList<GameModelResponse>()
                    for(row in games){
                        if(row.fields.name!!.toLowerCase().contains(charSearch.toLowerCase())){
                            resultList.add(row)
                        }
                    }
                    filterListResult = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterListResult
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                filterListResult = filterResults!!.values as List<GameModelResponse>
                notifyDataSetChanged()
            }

        }
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GameViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.game_list_item, p0, false)
        return GameViewHolder(itemView)
    }


    override fun onBindViewHolder(p0: GameViewHolder, position: Int) {
        val game = filterListResult[position]

        val gameImg = game.fields.imageURL

        Picasso.with(p0.itemView.context).load(gameImg).into(p0.gameImg)

        p0.gameTitle.text =
            game.fields.name



    }
    override fun getItemCount(): Int {
        return filterListResult.size
    }
    inner class GameViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var gameImg: ImageView = itemView.findViewById(R.id.game_img)
        var gameTitle: TextView = itemView.findViewById((R.id.game_title))
    }
}