package com.app.android.ibet.indicator

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.fragment.Live

import java.util.ArrayList

/**
 * Created by MQ on 2016/11/11.
 */

internal class GridViewAdapter(datas: List<DataBean>, page: Int) : BaseAdapter() {
    private val dataList: MutableList<DataBean>

    init {
        dataList = ArrayList()
        //start end分别代表要显示的数组在总数据List中的开始和结束位置
        var start = page * 3
        val end = start + 3
        while (start < datas.size && start < end) {
            dataList.add(datas[start])
            start++
        }
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(i: Int): Any {
        return dataList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, itemView: View?, viewGroup: ViewGroup): View {
        var view = itemView
        var holder: ViewHolder
        if (view == null) {
            val inflater = viewGroup.context?.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.slot_item,null)
            holder = ViewHolder()
            holder.iv_img = itemView!!.findViewById<View>(R.id.iv_img) as ImageView
        } else {
            holder = itemView!!.tag as ViewHolder
        }

        /*
        if (itemView == null) {

            itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.slot_item, viewGroup, false)
            holder = ViewHolder()
            holder.iv_img = itemView!!.findViewById<View>(R.id.iv_img) as ImageView
            //mHolder.tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            itemView.tag = holder
        } else {

        }
        */
        //val bean = dataList[i]



        holder.iv_img!!.setImageResource(R.mipmap.gameitem)
        //mHolder.tv_text = (TextView) itemView.findViewById(R.id.tv_text);

       // if (bean != null) {
          //  holder.iv_img!!.setImageResource(R.mipmap.gameitem)
            //mHolder.tv_text.setText(bean.name);
        //}
        return view!!
    }

    inner class ViewHolder {
         var iv_img: ImageView? = null
        //private TextView tv_text;
    }
}
