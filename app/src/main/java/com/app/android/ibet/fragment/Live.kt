package com.app.android.ibet.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import com.app.android.ibet.R
import com.app.android.ibet.indicator.*
import com.app.android.ibet.indicator.CusViewPagerAdapter
import com.app.android.ibet.indicator.GridViewAdapter
import kotlinx.android.synthetic.main.frag_live.*


import java.util.*

@SuppressLint("ValidFragment")
class Live : Fragment() {

    companion object {
        var item_grid_num = 9//每一页中GridView中item的数量
        var number_columns = 3//gridview一行展示的数目
    }
    //private lateinit var view_pager: ViewPager
    //private lateinit var mAdapter: ViewPagerAdapter
    lateinit var dataList: MutableList<DataBean>
    private val gridList = ArrayList<GridView>()
    //private lateinit var indicator: CirclePageIndicator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_live, container, false)
    }
    override fun onStart() {
        super.onStart()
        initViews()
        initDatas()
    }
    private fun initViews() {
        //初始化ViewPager

       // mAdapter = ViewPagerAdapter
        view_pager.adapter = CusViewPagerAdapter()
        dataList = ArrayList()
        //圆点指示器
        //indicator = findViewById<View>(R.id.indicator) as CirclePageIndicator
        indicator.setVisibility(View.VISIBLE)
        indicator.setViewPager(view_pager)
    }

    private fun initDatas() {
        if (dataList!!.size > 0) {
            dataList!!.clear()
        }
        if (gridList.size > 0) {
            gridList.clear()
        }
        //初始化数据
        for (i in 0..19) {
            val bean = DataBean()
            //bean.name = "第" + (i + 1) + "条数据"
            dataList!!.add(bean)
        }
        //计算viewpager一共显示几页
        val pageSize = if (dataList!!.size % item_grid_num == 0)
            dataList!!.size / item_grid_num
        else
            dataList!!.size / item_grid_num + 1
        for (i in 0 until pageSize) {
            val gridView = GridView(context)
            val adapter = GridViewAdapter(dataList!!, i)
            gridView.numColumns = number_columns
            gridView.adapter = adapter
            gridList.add(gridView)
        }
        CusViewPagerAdapter().add(gridList)
    }



}