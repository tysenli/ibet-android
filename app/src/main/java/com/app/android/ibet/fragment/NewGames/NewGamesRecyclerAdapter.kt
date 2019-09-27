package com.app.android.ibet.fragment.NewGames

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.android.ibet.R
import com.app.android.ibet.model.GameModelResponse
import com.squareup.picasso.Picasso

class NewGamesRecyclerAdapter(val games: List<GameModelResponse>):RecyclerView.Adapter<NewGamesRecyclerAdapter.GameViewHolder>()  {
    internal var newGames: List<GameModelResponse>

    init {

        this.newGames = games

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewGamesRecyclerAdapter.GameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_child_recycler, parent, false)
        return GameViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return newGames.size
    }

    override fun onBindViewHolder(holder: NewGamesRecyclerAdapter.GameViewHolder, position: Int) {
        val game = newGames[position]

        val gameImg = game.fields.imageURL

        Picasso.with(holder.itemView.context).load(gameImg).into(holder.gameImg)

        holder.gameTitle.text =
            game.fields.name
    }
    inner class GameViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var gameImg: ImageView = itemView.findViewById(R.id.newGame_img)
        var gameTitle: TextView = itemView.findViewById((R.id.newGame_name))

    }
}