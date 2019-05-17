package com.example.proj2.ibet

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.proj2.ibet.ViewModel.GameViewModel
//import com.example.proj2.ibet.activity.MusicDetailActivity
import com.example.proj2.ibet.model.GameModel

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_games.*
import kotlinx.android.synthetic.main.game_list_item.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [JokeList.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [JokeList.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
@SuppressLint("ValidFragment")
class Games(context: Context) : Fragment() {
    private var adapter = GameAdapter()
    private var parentContext: Context = context
    private lateinit var viewModel: GameViewModel

    private var GameList: ArrayList<GameModel> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onStart() {
        super.onStart()
        game_list.layoutManager = GridLayoutManager(this.context,2)
        //game_list.layoutManager = LinearLayoutManager(this.context)
        game_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        val observer = Observer<ArrayList<GameModel>> {
            game_list.adapter = adapter
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
                    return GameList[p0].getName() == GameList[p1].getName()
                }

                override fun getOldListSize(): Int {
                    return GameList.size
                }

                override fun getNewListSize(): Int {
                    if (it == null) {
                        return 0
                    }
                    return it.size
                }

                override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
                    return GameList[p0] == GameList[p1]
                }
            })
            result.dispatchUpdatesTo(adapter)
            GameList = it ?: ArrayList()
        }


        viewModel.getGames().observe(this, observer)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
    }

    inner class GameAdapter: RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GameViewHolder {
            val itemView = LayoutInflater.from(p0.context).inflate(R.layout.game_list_item, p0, false)
            return GameViewHolder(itemView)
        }

        override fun onBindViewHolder(p0: GameViewHolder, p1: Int) {
            val game = GameList[p1]
            val gameImages =
                game.getImage()

            Picasso.with(this@Games.parentContext).load(gameImages).into(p0.gameImg)

            p0.gameTitle.text =
                game.getName()


        }

        override fun getItemCount(): Int {
            return GameList.size
        }

        inner class GameViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var row = itemView

            var gameImg: ImageView = itemView.game_img
            var gameTitle: TextView = itemView.game_title
        }
    }
}
