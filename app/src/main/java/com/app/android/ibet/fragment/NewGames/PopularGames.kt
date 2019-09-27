package com.app.android.ibet.fragment.NewGames

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.model.GameModelResponse
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_new_games.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.HashMap
import com.app.android.ibet.api.URLs

class PopularGames: Fragment() {
    private lateinit var rootView: View
    private lateinit var newgame_recycler: RecyclerView
    private lateinit var filterListBody: ExpandableListView
    private lateinit var sortListView: ListView
    internal lateinit var listDataHeader: MutableList<String>
    internal lateinit var listDataChild: HashMap<String, List<String>>
    internal lateinit var listDataHeader1: MutableList<String>
    private lateinit var gameModel: List<GameModelResponse>
    private lateinit var newgame_text: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_new_games, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newgame_recycler = view.findViewById(R.id.newgame_recycler) as RecyclerView
        newgame_recycler.layoutManager = GridLayoutManager(this.context, 3)

        filterListBody = view.findViewById(R.id.newgame_filter_List_parents) as ExpandableListView
        sortListView =  view.findViewById(R.id.newgame_sort_List) as ListView
        prepareListData()
        prepareListData1()
        filterListBody.setAdapter(FilterExpandableListAdapter(listDataHeader, listDataChild))
        sortListView.adapter = SortListAdapter(listDataHeader1)

        newgame_text =view.findViewById(R.id.newgame_text) as TextView
        newgame_text.text = "Popular"

    }

    override fun onStart() {
        super.onStart()
        var filter = true
        var sort = true
        var saved = true

        fetchGames()

        newgame_filter_button.background = resources.getDrawable(R.color.colorPrimary)
        newgame_sort_button.background = resources.getDrawable(R.color.colorPrimary)
        newgame_filter_expan.visibility = View.GONE
        newgame_filter_button.setOnClickListener {
            sort = true
            newgame_sort_expan.visibility = View.GONE
            if(filter){
                newgame_filter_button.background = resources.getDrawable(R.color.brownish_grey)
                newgame_sort_button.background = resources.getDrawable(R.color.colorPrimary)
                newgame_filter_expan.bringToFront()
                newgame_filter_expan.visibility = View.VISIBLE
                filter = false
            }else{
                newgame_filter_button.background = resources.getDrawable(R.color.colorPrimary)
                newgame_sort_button.background = resources.getDrawable(R.color.colorPrimary)
                newgame_filter_expan.visibility = View.GONE
                filter = true
            }

        }
        newgame_sort_button.setOnClickListener {
            filter = true
            newgame_filter_expan.visibility = View.GONE
            if(sort){
                newgame_filter_button.background = resources.getDrawable(R.color.colorPrimary)
                newgame_sort_button.background = resources.getDrawable(R.color.brownish_grey)
                newgame_sort_expan.bringToFront()
                newgame_sort_expan.visibility = View.VISIBLE
                sort = false
            }else{
                newgame_filter_button.background = resources.getDrawable(R.color.colorPrimary)
                newgame_sort_button.background = resources.getDrawable(R.color.colorPrimary)
                newgame_sort_expan.visibility = View.GONE
                sort = true
            }
        }

    }
    private fun fetchGames(){
        val url = URLs.GAME_URL+ "live-casino" + URLs.GAME_URL_CATEGORY + "all" + URLs.GAME_URL_FILTER  + "Popular"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue((object: okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("TAG", "onFailure: "+e.toString() );
            }

            override fun onResponse(call: Call, response: Response) {

                val body = response?.body()?.string()
                Log.e("Success", body)

                val gson = GsonBuilder().create()
                gameModel = gson.fromJson(body, object : TypeToken<List<GameModelResponse>>() { }.type)
                this@PopularGames.activity!!.runOnUiThread {
                    newgame_recycler.adapter = NewGamesRecyclerAdapter(gameModel)

                }
            }

        }))
    }

    inner class SortListAdapter(private val listDataHeader: List<String>): BaseAdapter(){
        private val inf: LayoutInflater = LayoutInflater.from(getActivity());
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var convertView = convertView
            val holder: ViewHolder

            if (convertView == null) {

                val inflater = context!!
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = inflater.inflate(R.layout.sort_item_layout, null, true)
                holder = ViewHolder(convertView)
                holder.checkBox = convertView!!.findViewById(R.id.sortlist_check_box) as CheckBox
                holder.text = convertView!!.findViewById(R.id.sortlist_text) as TextView

                convertView.tag = holder
            } else {
                // the getTag returns the viewHolder object set as a tag to the view
                holder = convertView.tag as ViewHolder
            }
            holder.text!!.setText(listDataHeader[position])


            return convertView
        }

        override fun getItem(position: Int): Any {
            return listDataHeader[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return listDataHeader.size
        }
        private inner class ViewHolder(itemView: View) {
            var checkBox: CheckBox = itemView.findViewById(R.id.sortlist_check_box)
            var text: TextView = itemView.findViewById(R.id.sortlist_text)

        }
    }

    inner class FilterExpandableListAdapter(private val listDataHeader:List<String>, private val listDataChild: HashMap<String, List<String>>):
        BaseExpandableListAdapter(){

        private val inf: LayoutInflater = LayoutInflater.from(getActivity());


        override fun getGroup(groupPosition: Int): Any {
            return this.listDataHeader[groupPosition]
        }

        override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
            return true
        }

        override fun hasStableIds(): Boolean {
            return false
        }

        override fun getGroupView(
            groupPosition: Int,
            isExpanded: Boolean,
            convertView: View?,
            parent: ViewGroup?
        ): View {
            var convertView = convertView
            val headerTitle = getGroup(groupPosition) as String
            if (convertView == null) {
                //val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = inf.inflate(R.layout.filter_group_layout, parent, false)
            }
            val lblListHeader = convertView!!.findViewById<View>(R.id.filter_title) as TextView
            val unwrapIcon = convertView!!.findViewById<View>(R.id.filter_unwarp) as ImageView
            unwrapIcon.visibility = View.GONE
            lblListHeader.text = headerTitle

            if (isExpanded) {
                unwrapIcon.setImageDrawable(getDrawableFor(R.drawable.up))

            } else {
                unwrapIcon.setImageDrawable(getDrawableFor(R.drawable.back2))
            }

            unwrapIcon.visibility = View.VISIBLE

            return convertView
        }
        fun getDrawableFor(resource: Int): Drawable = ContextCompat.getDrawable(this@PopularGames.activity!!, resource)!!
        override fun getChildrenCount(groupPosition: Int): Int {
            return this.listDataChild[this.listDataHeader[groupPosition]]!!.size
        }

        override fun getChild(groupPosition: Int, childPosition: Int): Any {
            return this.listDataChild[this.listDataHeader[groupPosition]]!!.get(childPosition)
        }

        override fun getGroupId(groupPosition: Int): Long {
            return groupPosition.toLong()
        }

        override fun getChildView(
            groupPosition: Int,
            childPosition: Int,
            isLastChild: Boolean,
            convertView: View?,
            parent: ViewGroup?
        ): View {

            var convertView = convertView
            val childText = getChild(groupPosition, childPosition) as String
            if (convertView == null) {
                convertView = inf.inflate(R.layout.filter_child_layout, parent, false)
            }

            val txtListChild = convertView!!.findViewById<View>(R.id.child_text) as TextView
            txtListChild.text = childText

            return convertView

        }

        override fun getChildId(groupPosition: Int, childPosition: Int): Long {
            return childPosition.toLong()
        }

        override fun getGroupCount(): Int {
            return this.listDataHeader.size
        }

    }

    private fun prepareListData() {
        listDataHeader = java.util.ArrayList()
        listDataChild = HashMap()

        // Adding child data
        listDataHeader.add("Provider")
        listDataHeader.add("Features")
        listDataHeader.add("Theme")
        listDataHeader.add("Jackpot")

        // Adding child data
        val Provider = java.util.ArrayList<String>()
        Provider.add("Netent")
        Provider.add("Quickspin")
        Provider.add("Blueprint")
        Provider.add("Novomatic")
        Provider.add("IGT")
        Provider.add("Genesis")
        Provider.add("High5")

        val Features = java.util.ArrayList<String>()
        Features.add("Stacked Wilds")
        Features.add("Expanding Wilds")
        Features.add("Special Wilds")
        Features.add("Sticky Wilds")
        Features.add("Random Wilds")
        Features.add("Big Multipliers")

        val Theme = java.util.ArrayList<String>()
        Theme.add("Egypt")
        Theme.add("Fruits")
        Theme.add("Landbased")
        Theme.add("Dragons")
        Theme.add("Arabic")

        val Jackpot = java.util.ArrayList<String>()
        Jackpot.add("Daily")
        Jackpot.add("Mystery")
        Jackpot.add("Mega")
        Jackpot.add("Progressive")

        listDataChild[listDataHeader[0]] = Provider // Header, Child data
        listDataChild[listDataHeader[1]] = Features
        listDataChild[listDataHeader[2]] = Theme
        listDataChild[listDataHeader[3]] = Jackpot
    }

    private fun prepareListData1() {
        listDataHeader1 = java.util.ArrayList()

        // Adding child data
        listDataHeader1.add("Categories")
        listDataHeader1.add("Name (A-Z)")
        listDataHeader1.add("Popularity")
        listDataHeader1.add("Jackpot Size")


    }

}