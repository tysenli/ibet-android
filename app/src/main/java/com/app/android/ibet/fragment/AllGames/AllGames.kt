package com.app.android.ibet.fragment.AllGames

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
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
import java.util.HashMap


class AllGames: Fragment(){

    private lateinit var rootView: View
    private lateinit var filterListBody: ExpandableListView
    private lateinit var sortListView: ListView

//    private var groups: ArrayList<String> = arrayListOf()
//    private var children: ArrayList<List<String>> = arrayListOf()
    internal lateinit var listDataHeader: MutableList<String>
    internal lateinit var listDataChild: HashMap<String, List<String>>

    internal lateinit var listDataHeader1: MutableList<String>



//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        fetchFilter()
//
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_all_games, container, false)
        //filterListBody = rootView.findViewById(R.id.filter_List_parents) as ExpandableListView

//        filterListAdapter.setOnFilterItemClickListener(this)
//        filterListBody.setAdapter(FilterExpandableAdapter())
//        filterListBody.setOnGroupClickListener(filterListAdapter)
//        filterListBody.setOnChildClickListener(filterListAdapter)
//        filterListAdapter.showItems(SampleFilter.getFilter())
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterListBody = view.findViewById(R.id.filter_List_parents) as ExpandableListView
        sortListView =  view.findViewById(R.id.sort_List) as ListView
        prepareListData()
        prepareListData1()
        filterListBody.setAdapter(FilterExpandableListAdapter(listDataHeader, listDataChild))
        sortListView.adapter = SortListAdapter(listDataHeader1)



    }

    override fun onStart() {
        super.onStart()
        var filter = true
        var sort = true
        filter_button.background = resources.getDrawable(R.color.colorPrimary)
        sort_button.background = resources.getDrawable(R.color.colorPrimary)
        filter_expan.visibility = View.GONE
        filter_button.setOnClickListener {
            sort = true

            sort_expan.visibility = View.GONE
            if(filter){
                filter_button.background = resources.getDrawable(R.color.brownish_grey)
                sort_button.background = resources.getDrawable(R.color.colorPrimary)
                filter_expan.visibility = View.VISIBLE
                filter = false
            }else{
                filter_button.background = resources.getDrawable(R.color.colorPrimary)
                sort_button.background = resources.getDrawable(R.color.colorPrimary)
                filter_expan.visibility = View.GONE
                filter = true
            }

        }
        sort_button.setOnClickListener {
            filter = true

            filter_expan.visibility = View.GONE
            if(sort){
                filter_button.background = resources.getDrawable(R.color.colorPrimary)
                sort_button.background = resources.getDrawable(R.color.brownish_grey)
                sort_expan.visibility = View.VISIBLE
                sort = false
            }else{
                filter_button.background = resources.getDrawable(R.color.colorPrimary)
                sort_button.background = resources.getDrawable(R.color.colorPrimary)
                sort_expan.visibility = View.GONE
                sort = true
            }



        }

    }


//    private fun fetchFilter(){
//        val client = OkHttpClient()
//        val url = BuildConfig.GAME_FILTER
//        val request = Request.Builder().url(url).build()
//        client.newCall(request).enqueue((object: okhttp3.Callback{
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("TAG", "onFailure: "+e.toString() );
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//
//                val body = response?.body()?.string()
//                Log.e("Success", body)
//                val jsonArray = JSONArray(body)
//                print(jsonArray)
//                val len = jsonArray.length()
//                val arr1 = jsonArray.getJSONObject(1)
//                val name = arr1.get("data")
//                print(name)
//                val list = ArrayList<String>()
//                for (i in 0..(len - 1)){
//                    val arr = jsonArray.getJSONObject(i)
//                    val name = arr.get("name").toString()
//                    val data = arr.get("data").toString().replace("[","").replace("]","")
//
//                    groups.add(name)
//                    children.add(listOf(data))
//
//                }
//                Log.e("Success", groups.toString())
//                Log.e("Success", children.toString())
//
//            }
//
//        }))
//    }
    inner class SortListAdapter(private val listDataHeader: List<String>): BaseAdapter(){
        private val inf:LayoutInflater = LayoutInflater.from(getActivity());
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

    inner class FilterExpandableListAdapter(private val listDataHeader:List<String>, private val listDataChild:HashMap<String, List<String>>):BaseExpandableListAdapter(){
//        private val groups: ArrayList<String>
//        private val children: ArrayList<List<String>>
        private val inf:LayoutInflater = LayoutInflater.from(getActivity());


//        init{
//            this.groups = groups
//            this.children = children
//            inf = LayoutInflater.from(getActivity())
//        }
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


            //val itemView = inf.inflate(R.layout.filter_group_layout, parent, false)
//            val holder:ViewHolder
//            convertView?.let {
//                convertView
//            }  ?: itemView
//
//            holder = ViewHolder(itemView)
//            holder.group_text = convertView?.findViewById(R.id.filter_title) as TextView
//            convertView.setTag(holder)
//            holder.group_text.setText(getGroup(groupPosition).toString())
            return convertView
        }
        fun getDrawableFor(resource: Int): Drawable = ContextCompat.getDrawable(this@AllGames.activity!!, resource)!!
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
//            val itemView = inf.inflate(R.layout.filter_child_layout, parent, false)
//            val holder: ViewHolder
//            convertView?.let {
//                convertView
//            }  ?: itemView
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
//            holder = ViewHolder(itemView)
//            holder.child_text = convertView?.findViewById(R.id.child_text) as TextView
//            convertView.setTag(holder)
//            holder.child_text.setText(getChild(groupPosition, childPosition).toString())
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

//        inner class ViewHolder(itemView: View) {
//            internal var group_text: TextView = itemView.findViewById(R.id.filter_title)
//            internal var child_text: TextView = itemView.findViewById(R.id.child_text)
//        }

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


