package com.app.android.ibet.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import kotlinx.android.synthetic.main.frag_display.*
import java.util.*

class Display : Fragment() {
    private var parentContext = context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_display, container, false)
    }

    override fun onStart() {
        super.onStart()
        var language = arrayOf("Language", "English", "Chinese", "Thai")
        var lanImg = arrayOf(R.drawable.lan_bng, R.drawable.gb, R.drawable.cn, R.drawable.th)
        // var flag[] = {R.drawable.gb, R.drawable.cn, R.drawable.th}
       // var lanSpinner = findViewById<Spinner>(R.id.language_navi)
        var lan: String = ""
        if (language_navi != null) {
            val arrayAdapter = CustomDropDownAdapter(context!!, lanImg, language)
            //CusAdapter(this, flag, language)
            language_navi.adapter = arrayAdapter

            language_navi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    //Toast.makeText(this, getString(R.string.selected_item) + " " + gender[position], Toast.LENGTH_SHORT).show()

                    when (position) {
                        1 -> {
                            language[0] = "English"
                            lanImg[0] = R.drawable.gb
                            val locale = Locale("en")
                            val config = Configuration()
                            config.locale = locale
                            context!!.resources.updateConfiguration(
                                config,
                                context!!.resources.displayMetrics
                            )
                            startActivity(Intent(context, MainActivity::class.java))
                        }
                        2 -> {
                            language[0] = "Chinese"
                            lanImg[0] = R.drawable.cn
                            val locale = Locale("zh")
                            val config = Configuration()
                            config.locale = locale
                            context!!.resources.updateConfiguration(
                                config,
                                context!!.resources.displayMetrics
                            )
                            startActivity(Intent(context, MainActivity::class.java))
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }

        }
    }
}

class CustomDropDownAdapter(val context: Context, var image : Array<Int>, var listItemsTxt: Array<String>) : BaseAdapter() {


    val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.custom_spinner_items, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        // setting adapter item height programatically.

        val params = view.layoutParams
        params.height = 80
        view.layoutParams = params

        vh.label.text = listItemsTxt.get(position)
        vh.img.setImageResource(image[position])
        return view
    }

    override fun getItem(position: Int): Any? {

        return null

    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getCount(): Int {
        return listItemsTxt.size
    }

    private class ItemRowHolder(row: View?) {

        val label: TextView
        val img : ImageView

        init {
            this.label = row?.findViewById(R.id.language_txt) as TextView
            this.img = row?.findViewById(R.id.language_img) as ImageView
        }
    }
}