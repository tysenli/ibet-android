package com.app.android.ibet.activity.UserProfile.Analysis

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.android.ibet.R
import com.idtk.smallchart.chart.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.components.XAxis
import android.R.color
import android.R.attr.colorPrimaryDark
import android.support.v4.content.ContextCompat
import android.R.attr.colorPrimary
import com.github.mikephil.charting.data.LineDataSet

import android.graphics.Color
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import kotlinx.android.synthetic.main.frag_analysis.*

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.highlight.Highlight


class Analysis : Fragment() {
    private var parentContext = context
    lateinit var charts : LineChart


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_analysis, container, false)
    }

    override fun onStart() {
        super.onStart()
        aly_bet.background = resources.getDrawable(R.color.btn_d)
        aly_bank.background = resources.getDrawable(R.color.btn_l)
        aly_sport.visibility = View.VISIBLE
        aly_slot.visibility = View.VISIBLE
        aly_casino.visibility = View.VISIBLE
        aly_depo.visibility = View.GONE
        aly_with.visibility = View.GONE
        //chart = findViewById(R.id.chart)

        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 20f))
        entries.add(Entry(1f, 30f))
        entries.add(Entry(2f, 40f))
        entries.add(Entry(3f, 30f))
        entries.add(Entry(4f, 20f))
        entries.add(Entry(5f, 50f))
        entries.add(Entry(6f, 60f))
        entries.add(Entry(7f, 40f))
        entries.add(Entry(8f, 30f))
        entries.add(Entry(9f, 50f))

        val dataSet = LineDataSet(entries,"")
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        val drawable = activity!!.resources.getDrawable(R.drawable.fade_blue)
        dataSet.fillDrawable = drawable
        dataSet.setDrawFilled(true)
        dataSet.setDrawValues(false)
        dataSet.highLightColor = Color.rgb(235,238,242)
        chart.setTouchEnabled(true)
        chart.legend.isEnabled = false
        val mv = CustomMarkerView(context!!,R.layout.chart_data_show)
        chart.marker = mv
        //dataSet.color = ContextCompat.getColor(parentContext!!, R.color.colorPrimary)
        //dataSet.valueTextColor = ContextCompat.getColor(parentContext!!, R.color.colorPrimaryDark)

        //****
        // Controlling X axis
        val xAxis = chart.xAxis
        // Set the xAxis position to bottom. Default is top
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //Customizing x axis value
        val months = arrayOf("7/1", "7/2", "7/3", "7/4", "7/5", "7/6", "7/7", "7/8", "7/9", "7/10")

        val formatter = IAxisValueFormatter { value, axis -> months[value.toInt()] }
        xAxis.granularity = 1f // minimum axis-step (interval) is 1

        xAxis.setValueFormatter(formatter)

        //***
        // Controlling right side of y axis
        val yAxisRight = chart.axisRight
        yAxisRight.isEnabled = false

        //***
        // Controlling left side of y axis
        val yAxisLeft = chart.axisLeft
        yAxisLeft.granularity = 1f

        // Setting Data
        val data = LineData(dataSet)
        chart.setData(data)
        chart.animateX(2500)
        //refresh
        chart.invalidate()

        aly_bet.setOnClickListener {
            aly_bet.background = resources.getDrawable(R.color.btn_d)
            aly_bank.background = resources.getDrawable(R.color.btn_l)
            aly_sport.visibility = View.VISIBLE
            aly_slot.visibility = View.VISIBLE
            aly_casino.visibility = View.VISIBLE
            aly_depo.visibility = View.GONE
            aly_with.visibility = View.GONE
        }
        aly_bank.setOnClickListener {
            aly_bet.background = resources.getDrawable(R.color.btn_l)
            aly_bank.background = resources.getDrawable(R.color.btn_d)
            aly_sport.visibility = View.GONE
            aly_slot.visibility = View.GONE
            aly_casino.visibility = View.GONE
            aly_depo.visibility = View.VISIBLE
            aly_with.visibility = View.VISIBLE

        }

    }
}

class CustomMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {

    private val tvContent: TextView

    // this will center the marker-view horizontally
    val xOffset: Int
        get() = -(width / 2)

    // this will cause the marker-view to be above the selected value
    val yOffset: Int
        get() = -height

    init {
        // this markerview only displays a textview

        tvContent = findViewById<View>(R.id.tvContent) as TextView
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight) {
        tvContent.text = "" + e.y // set the entry-value as the display text
    }
}