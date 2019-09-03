package com.app.android.ibet.activity.Navigation

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import com.app.android.ibet.R


class MenuExpandableAdapter(private val context: Context) :
    BaseExpandableListAdapter(),
    ExpandableListView.OnGroupClickListener,
    ExpandableListView.OnChildClickListener {

    private var menuItems = mutableListOf<NaviMenuItem>()
    private var onMenuItemClickListener: OnMenuItemClick = EmptyListener()

    override fun getGroup(groupPosition: Int): Any = menuItems[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        val selectedGroup = menuItems[groupPosition]

        return selectedGroup.let {
            selectedGroup.subMenus[childPosition]
        }
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view = getView(convertView, R.layout.menu_group)

        val group = menuItems[groupPosition]

        val title = view.findViewById<TextView>(R.id.group_title)
        val icon = view.findViewById<ImageView>(R.id.group_icon)
        val unwrapIcon = view.findViewById<ImageView>(R.id.unwrap)
        val badge = view.findViewById<TextView>(R.id.group_badge)

        unwrapIcon.visibility = View.GONE
        badge.visibility = View.GONE

        title.text = group.title
        icon.setImageDrawable(getDrawableFor(group.iconRes))

        if (getChildrenCount(groupPosition) > 0) {
            if (isExpanded) {
                unwrapIcon.setImageDrawable(getDrawableFor(R.drawable.up))

            } else {
                unwrapIcon.setImageDrawable(getDrawableFor(R.drawable.back2))
            }

            unwrapIcon.visibility = View.VISIBLE
        }

        if (group.badget > 0) {
            badge.text = group.badget.toString()
            badge.visibility = View.VISIBLE
        }

        return view
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view = getView(convertView, R.layout.menu_child)

        val child = menuItems[groupPosition].subMenus[childPosition]

        child.let {
            val title = view.findViewById<TextView>(R.id.child_title)
            val icon = view.findViewById<ImageView>(R.id.child_icon)
            val badge = view.findViewById<TextView>(R.id.child_badge)

            badge.visibility = View.GONE

            title.text = child.title
            icon.setImageDrawable(getDrawableFor(child.iconRes))

            if (child.badget > 0) {
                badge.text = child.badget.toString()
                badge.visibility = View.VISIBLE
            }
        }

        return view
    }

    override fun getGroupCount(): Int = menuItems.size

    override fun getChildrenCount(groupPosition: Int): Int = menuItems[groupPosition].subMenus.size

    override fun getGroupId(groupPosition: Int): Long = menuItems[groupPosition].id.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = menuItems[groupPosition].subMenus[childPosition].id.toLong()

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    override fun hasStableIds(): Boolean = true

    private fun getView(convertView: View?, @LayoutRes layoutRes: Int): View {
        return convertView?.let {
            convertView
        } ?: LayoutInflater.from(context).inflate(layoutRes, null, false)
    }

    private fun getDrawableFor(resource: Int): Drawable = ContextCompat.getDrawable(context, resource)!!

    fun showItems(items: MutableList<NaviMenuItem>) {
        menuItems = items
        notifyDataSetChanged()
    }

    fun addItemAt(position: Int, menuItem: NaviMenuItem) {
        try {
            menuItems.add(position, menuItem)
            notifyDataSetChanged()

        } catch (exception: Exception) {
            Log.w("Menu", "Error adding Item, ${exception.message}")
        }
    }

    fun removeItemAt(position: Int) {
        try {
            menuItems.removeAt(position)
            notifyDataSetChanged()

        } catch (exception: Exception) {
            Log.w("Menu", "Error removing Item, ${exception.message}")
        }
    }

    fun updateItem(menuItem: NaviMenuItem) {
        menuItems.forEach{
            if (it.id == menuItem.id) {
                it.title = menuItem.title
                it.titleRes = menuItem.titleRes
                it.iconRes = menuItem.iconRes
                it.badget = menuItem.badget
                it.subMenus = menuItem.subMenus

                notifyDataSetChanged()
                return
            }
        }
    }

    fun setOnMenuItemClickListener(listener: OnMenuItemClick?) {
        this.onMenuItemClickListener = listener?.let { it } ?: EmptyListener()
    }

    override fun onGroupClick(parent: ExpandableListView?, v: View?, groupPosition: Int, id: Long): Boolean {
        onMenuItemClickListener.onMenuClick(groupPosition, menuItems[groupPosition])
        return false
    }

    override fun onChildClick(parent: ExpandableListView?, v: View?, groupPosition: Int, childPosition: Int, id: Long): Boolean {
        onMenuItemClickListener.onSubMenuClick(childPosition, menuItems[groupPosition].subMenus[childPosition])
        return false
    }

    interface OnMenuItemClick {
        fun onMenuClick(position: Int, menuItem: NaviMenuItem)

        fun onSubMenuClick(position: Int, menuItem: NaviMenuItem)
    }
}