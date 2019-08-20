package com.app.android.ibet.indicator

import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.GridView

import java.util.ArrayList

/**
 * Created by MQ on 2016/11/11.
 */

internal class CusViewPagerAdapter : PagerAdapter() {
    private val gridList: MutableList<GridView>

    init {
        gridList = ArrayList()
    }

    fun add(datas: List<GridView>) {
        if (gridList.size > 0) {
            gridList.clear()
        }
        gridList.addAll(datas)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return gridList.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(gridList[position])
        return gridList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
