package com.app.android.ibet.fragment.AllGames

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import com.app.android.ibet.R
import androidx.core.content.ContextCompat.getSystemService





class FilterExpandableAdapter(private val context: Context): BaseExpandableListAdapter(),ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener{

    private var filterItem = mutableListOf<FilterListItem>()

    private var onFilterItemClickListener: OnFilterItemClick = EmptyFilterListener()

    override fun getGroup(groupPosition: Int): Any = filterItem[groupPosition]
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val selectedGroup = filterItem[groupPosition]
        return selectedGroup.let {
            selectedGroup.data[childPosition]
        }
    }
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view = getView(convertView, R.layout.filter_group_layout)
        val group = filterItem[groupPosition]
        val subFilter_name = view.findViewById<TextView>(R.id.filter_title)
        subFilter_name.text = group.name
        return view
    }
    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        val view = getView(convertView, R.layout.filter_child_layout)

        val child = filterItem[groupPosition].data[childPosition]

        child.let {
            val title = view.findViewById<CheckBox>(R.id.subFilter)
            title.text = child.name
        }

        return view
    }
    override fun getGroupCount(): Int = filterItem.size

    override fun getChildrenCount(groupPosition: Int): Int = filterItem[groupPosition].data.size

    override fun getGroupId(groupPosition: Int): Long = filterItem[groupPosition].id.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
    override fun hasStableIds(): Boolean = true

    private fun getView(convertView: View?, @LayoutRes layoutRes: Int): View {
        return convertView?.let {
            convertView
        } ?: LayoutInflater.from(context).inflate(layoutRes, null, false)
    }
    fun showItems(items: MutableList<FilterListItem>) {
        filterItem = items
        notifyDataSetChanged()
    }
    fun setOnFilterItemClickListener(listener: OnFilterItemClick?){
        this.onFilterItemClickListener = listener?.let { it } ?: EmptyFilterListener()
    }
    override fun onGroupClick(parent: ExpandableListView?, v: View?, groupPosition: Int, id: Long): Boolean {
        onFilterItemClickListener.onFilterClick(groupPosition, filterItem[groupPosition])
        return false
    }

    override fun onChildClick(
        parent: ExpandableListView?,
        v: View?,
        groupPosition: Int,
        childPosition: Int,
        id: Long
    ): Boolean {
        onFilterItemClickListener.onSubFilterClick(childPosition, filterItem[groupPosition].data[childPosition])
        return false
    }
    interface OnFilterItemClick {
        fun onFilterClick(position: Int, menuItem: FilterListItem)

        fun onSubFilterClick(position: Int, menuItem: FilterListItem)
    }
}

