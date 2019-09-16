spackage com.app.android.ibet.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
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
        Log.e("language", resources.configuration.locale.toString())
        var language = arrayOf("Language", "English", "Chinese", "Thai")
        var lanImg = arrayOf(R.drawable.lan_bng, R.drawable.gb, R.drawable.cn, R.drawable.th)
        if (resources.configuration.locale.toString() == "en" || resources.configuration.locale.toString() == "en_US") {
            language[0] = "English"
            lanImg[0] = R.drawable.gb
            europe_logo.visibility = View.VISIBLE
            europe_payment.visibility = View.VISIBLE
            chinese_logo.visibility = View.GONE
            chinese_payment_one.visibility = View.GONE
            chinese_payment_two.visibility = View.GONE
        }
        if (resources.configuration.locale.toString() == "zh") {
            language[0] = "Chinese"
            lanImg[0] = R.drawable.cn
            europe_logo.visibility = View.GONE
            europe_payment.visibility = View.GONE
            chinese_logo.visibility = View.VISIBLE
            chinese_payment_one.visibility = View.VISIBLE
            chinese_payment_two.visibility = View.VISIBLE
        }

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
                    language[0] = ""
                    lanImg[0] = R.drawable.lan_bng
                    when (position) {
                        1 -> {
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