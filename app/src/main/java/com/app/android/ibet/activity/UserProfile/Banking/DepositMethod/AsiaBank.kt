package com.app.android.ibet.activity.UserProfile.Banking.DepositMethod

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.api.Api
import com.app.android.ibet.fragment.CustomDropDownAdapter
import kotlinx.android.synthetic.main.frag_asiabank.*
import kotlinx.android.synthetic.main.frag_display.*
import java.util.*

class AsiaBank : Fragment() {
    //private var parentContext = context
    var userData = Api().get(BuildConfig.USER)
    var orderId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_asiabank, container, false)
    }

    override fun onStart() {
        super.onStart()
        var bankImg = arrayOf(R.drawable.guangfabank, R.drawable.bank_of_china, R.drawable.industrial, R.drawable.china_merchant, R.drawable.china_construction_bank_logo)
        val arrayAdapter = CustomDropDownAdapter(context!!, bankImg)
        //CusAdapter(this, flag, language)
        bank_choose.adapter = arrayAdapter
        bank_choose.prompt = "Choose Bank"
        bank_choose.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                //Toast.makeText(this, getString(R.string.selected_item) + " " + gender[position], Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
    }
}


class CustomDropDownAdapter(val context: Context, var image : Array<Int>) : BaseAdapter() {


    val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.custom_spinner_bank, parent, false)
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
        return image.size
    }

    private class ItemRowHolder(row: View?) {
        val img : ImageView

        init {
            this.img = row?.findViewById(R.id.bank_img) as ImageView
        }
    }
}