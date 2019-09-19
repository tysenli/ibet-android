package com.app.android.ibet.fragment.AllGames

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.fragment.FilterAdapter
import com.app.android.ibet.model.FilterModel
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.frag_visa.*
import kotlinx.android.synthetic.main.fragment_all_games.*
import kotlinx.android.synthetic.main.game_lobby_fragment.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import com.google.gson.Gson
import androidx.recyclerview.widget.RecyclerView


class AllGames: Fragment(){

    private lateinit var rootView: View
    private lateinit var filterListBody: ExpandableListView

    private var groups: ArrayList<String> = arrayListOf()
    private var children: ArrayList<List<String>> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchFilter()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_all_games, container, false)
        //filterListBody = v.findViewById(R.id.filter_List_parents)

//        filterListAdapter = FilterExpandableAdapter()
//        filterListAdapter.setOnFilterItemClickListener(this)
//        filterListBody.setAdapter(FilterExpandableAdapter())
//        filterListBody.setOnGroupClickListener(filterListAdapter)
//        filterListBody.setOnChildClickListener(filterListAdapter)
//        filterListAdapter.showItems(SampleFilter.getFilter())
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterListBody = view.findViewById(R.id.filter_List_parents)
        filterListBody.setAdapter(FilterExpandableListAdapter(groups, children))
        filterListBody.setGroupIndicator(null)

    }

//    override fun onStart() {
//        super.onStart()
//        filter_button.background = resources.getDrawable(R.color.colorPrimary)
//        sort_button.background = resources.getDrawable(R.color.colorPrimary)
//        filter_expan.visibility = View.GONE
//        filter_button.setOnClickListener {
//            filter_button.background = resources.getDrawable(R.color.brownish_grey)
//            sort_button.background = resources.getDrawable(R.color.colorPrimary)
//            filter_expan.visibility = View.VISIBLE
//
//        }
//        sort_button.setOnClickListener {
//            filter_button.background = resources.getDrawable(R.color.colorPrimary)
//            sort_button.background = resources.getDrawable(R.color.brownish_grey)
//            filter_expan.visibility = View.GONE
//        }
//
//    }


    private fun fetchFilter(){
        val client = OkHttpClient()
        val url = BuildConfig.GAME_FILTER
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue((object: okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("TAG", "onFailure: "+e.toString() );
            }

            override fun onResponse(call: Call, response: Response) {

                val body = response?.body()?.string()
                Log.e("Success", body)
                val jsonArray = JSONArray(body)
                print(jsonArray)
                val len = jsonArray.length()
                val arr1 = jsonArray.getJSONObject(1)
                val name = arr1.get("data")
                print(name)
                val list = ArrayList<String>()
                for (i in 0..(len - 1)){
                    val arr = jsonArray.getJSONObject(i)
                    val name = arr.get("name").toString()
                    val data = arr.get("data").toString().replace("[","").replace("]","")

                    groups.add(name)
                    children.add(listOf(data))

                }
                Log.e("Success", groups.toString())
                Log.e("Success", children.toString())

            }

        }))
    }
    inner class FilterExpandableListAdapter(groups:ArrayList<String>, children:ArrayList<List<String>>):BaseExpandableListAdapter(){
        private val groups: ArrayList<String>
        private val children: ArrayList<List<String>>
        private val inf:LayoutInflater

        init{
            this.groups = groups
            this.children = children
            inf = LayoutInflater.from(getActivity())
        }
        override fun getGroup(groupPosition: Int): Any {
            return groups[groupPosition]
        }

        override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
            return true
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun getGroupView(
            groupPosition: Int,
            isExpanded: Boolean,
            convertView: View?,
            parent: ViewGroup?
        ): View {
            val itemView = inf.inflate(R.layout.filter_group_layout, parent, false)
            val holder:ViewHolder
            convertView?.let {
                convertView
            }  ?: itemView

            holder = ViewHolder(itemView)
            holder.text = convertView?.findViewById(R.id.filter_title) as TextView
            convertView.setTag(holder)
            holder.text.setText(getGroup(groupPosition).toString())
            return convertView
        }

        override fun getChildrenCount(groupPosition: Int): Int {
            return children[groupPosition].size
        }

        override fun getChild(groupPosition: Int, childPosition: Int): Any {
            return children[groupPosition][childPosition]
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
            val itemView = inf.inflate(R.layout.filter_child_layout, parent, false)
            val holder: ViewHolder
            convertView?.let {
                convertView
            }  ?: itemView
//            if (convertView == null)
//            {
//                convertView = inf.inflate(R.layout.filter_child_layout, parent, false)
//                holder = RecyclerView.ViewHolder()
//                holder.text = convertView.findViewById(R.id.lblListItem) as TextView
//                convertView.setTag(holder)
//            }
//            else
//            {
//                holder = convertView.getTag() as ViewHolder
//            }
            holder = ViewHolder(itemView)
            holder.checkbox = convertView?.findViewById(R.id.subFilter) as CheckBox
            convertView.setTag(holder)
            holder.text.setText(getChild(groupPosition, childPosition).toString())
            return convertView
        }

        override fun getChildId(groupPosition: Int, childPosition: Int): Long {
            return childPosition.toLong()
        }

        override fun getGroupCount(): Int {
            return groups.size
        }
        inner class ViewHolder(itemView: View) {
            internal var text: TextView = itemView.findViewById(R.id.filter_title)
            internal var checkbox: CheckBox = itemView.findViewById(R.id.subFilter)
        }

    }


}


