package com.app.android.ibet.activity.UserProfile.Analysis

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import androidx.core.content.ContextCompat
import android.R.attr.colorPrimary
import com.github.mikephil.charting.data.LineDataSet

import android.graphics.Color
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import kotlinx.android.synthetic.main.frag_analysis.*

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.info
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.highlight.Highlight
import java.util.*
import kotlin.collections.ArrayList


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

        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        var curYear = calendar.get(Calendar.YEAR)
        var curMon = month + 1
        val monthMap = mapOf(1 to "January", 2 to "February", 3 to "March", 4 to "April", 5 to "May", 6 to "June", 7 to "July",
            8 to "August", 9 to "September", 10 to "October", 11 to "November", 12 to "December")
        if (curMon == 0) {
            curMon = 12
            curYear--
        }
        if (curMon == 13) {
            curMon = 1
            curYear++
        }
        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, (0..60).random().toFloat()))
        entries.add(Entry(1f, (0..60).random().toFloat()))
        entries.add(Entry(2f, (0..60).random().toFloat()))
        entries.add(Entry(3f, (0..60).random().toFloat()))
        entries.add(Entry(4f, (0..60).random().toFloat()))
        entries.add(Entry(5f, (0..60).random().toFloat()))
        entries.add(Entry(6f, (0..60).random().toFloat()))
        entries.add(Entry(7f, (0..60).random().toFloat()))
        entries.add(Entry(8f, (0..60).random().toFloat()))
        entries.add(Entry(9f, (0..60).random().toFloat()))
        curMonth.text = monthMap[curMon] + curYear
        prevMonth.text = monthMap[curMon - 1]
        nextMonth.text = monthMap[curMon + 1]
        Log.e("month", monthMap[month + 1])
        prevMonth.setOnClickListener {
            curMon --
            if (curMon == 0) {
                curMon = 12
                curYear--
            }
            curMonth.text = monthMap[curMon] + curYear
            prevMonth.text = if (curMon - 1 == 0) monthMap[12] else monthMap[curMon - 1]
            nextMonth.text = if (curMon + 1 == 13) monthMap[1] else monthMap[curMon + 1]
            entries.clear()
            entries.add(Entry(0f, (0..60).random().toFloat()))
            entries.add(Entry(1f, (0..60).random().toFloat()))
            entries.add(Entry(2f, (0..60).random().toFloat()))
            entries.add(Entry(3f, (0..60).random().toFloat()))
            entries.add(Entry(4f, (0..60).random().toFloat()))
            entries.add(Entry(5f, (0..60).random().toFloat()))
            entries.add(Entry(6f, (0..60).random().toFloat()))
            entries.add(Entry(7f, (0..60).random().toFloat()))
            entries.add(Entry(8f, (0..60).random().toFloat()))
            entries.add(Entry(9f, (0..60).random().toFloat()))

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
            val months = arrayOf("$curMon/1", "$curMon/2", "$curMon/3", "$curMon/4", "$curMon/5", "$curMon/6", "$curMon/7", "$curMon/8", "$curMon/9", "$curMon/10")

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
        }
        nextMonth.setOnClickListener {
            curMon++
            if (curMon == 13) {
                curMon = 1
                curYear++
            }
            curMonth.text = monthMap[curMon] + curYear
            prevMonth.text = if (curMon - 1 == 0) monthMap[12] else monthMap[curMon - 1]
            nextMonth.text = if (curMon + 1 == 13) monthMap[1] else monthMap[curMon + 1]

            entries.clear()
            entries.add(Entry(0f, (0..60).random().toFloat()))
            entries.add(Entry(1f, (0..60).random().toFloat()))
            entries.add(Entry(2f, (0..60).random().toFloat()))
            entries.add(Entry(3f, (0..60).random().toFloat()))
            entries.add(Entry(4f, (0..60).random().toFloat()))
            entries.add(Entry(5f, (0..60).random().toFloat()))
            entries.add(Entry(6f, (0..60).random().toFloat()))
            entries.add(Entry(7f, (0..60).random().toFloat()))
            entries.add(Entry(8f, (0..60).random().toFloat()))
            entries.add(Entry(9f, (0..60).random().toFloat()))

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
            val months = arrayOf("$curMon/1", "$curMon/2", "$curMon/3", "$curMon/4", "$curMon/5", "$curMon/6", "$curMon/7", "$curMon/8", "$curMon/9", "$curMon/10")


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
        }


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
        val months = arrayOf("$curMon/1", "$curMon/2", "$curMon/3", "$curMon/4", "$curMon/5", "$curMon/6", "$curMon/7", "$curMon/8", "$curMon/9", "$curMon/10")


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
            total_text.text = "Total Win/Loss"
            total_amt.text = "$395"
            aly_bet.background = resources.getDrawable(R.color.btn_d)
            aly_bank.background = resources.getDrawable(R.color.btn_l)
            aly_sport.visibility = View.VISIBLE
            aly_slot.visibility = View.VISIBLE
            aly_casino.visibility = View.VISIBLE
            aly_depo.visibility = View.GONE
            aly_with.visibility = View.GONE
        }
        aly_bank.setOnClickListener {
            total_text.text = "Total Net Position"
            total_amt.text = "$250"
            aly_bet.background = resources.getDrawable(R.color.btn_l)
            aly_bank.background = resources.getDrawable(R.color.btn_d)
            aly_sport.visibility = View.GONE
            aly_slot.visibility = View.GONE
            aly_casino.visibility = View.GONE
            aly_depo.visibility = View.VISIBLE
            aly_with.visibility = View.VISIBLE

        }
        aly_sport.setOnClickListener {
            info = "sports"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        aly_slot.setOnClickListener {
            info = "slots"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        aly_casino.setOnClickListener {
            info = "casino"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        aly_depo.setOnClickListener {
            info = "depo&with"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
        }
        aly_with.setOnClickListener {
            info = "depo&with"
            startActivity(Intent(activity, MyAccount::class.java))
            activity!!.overridePendingTransition(0, 0)
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