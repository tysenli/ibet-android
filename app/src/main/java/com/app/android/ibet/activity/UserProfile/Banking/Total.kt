package com.app.android.ibet.activity.UserProfile.Banking

import android.content.Intent
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.R
import com.app.android.ibet.activity.Login.Login
import com.app.android.ibet.activity.Login.Login.Companion.token
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.Signup.Signup
import com.app.android.ibet.activity.UserProfile.MyAccount
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.amt
import com.app.android.ibet.activity.UserProfile.MyAccount.Companion.amtShow
import com.idtk.smallchart.data.CurveData
import com.idtk.smallchart.data.PointShape
import com.idtk.smallchart.interfaces.iData.ICurveData
import kotlinx.android.synthetic.main.activity_total.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class Total : AppCompatActivity() {
    private val mDataList = ArrayList<ICurveData>()
    private val mCurveData = CurveData()
    private val mPointArrayList = ArrayList<PointF>()
    //lateinit var  amtShow :Button
    //private var amt = ""
    protected var points = arrayOf(
        floatArrayOf(1f, 2f),
        floatArrayOf(2f, 20f),
        floatArrayOf(3f, 20f),
        floatArrayOf(4f, 28f),
        floatArrayOf(5f, 25f),
        floatArrayOf(6f, 22f)

    )
    /*
    protected var mColors =
        intArrayOf(-0x330100, -0x9b6a13, -0x1cd9ca, -0x800000, -0x7f8000, -0x7397, -0x7f7f80, -0x194800, -0x830400)
    */
    protected fun pxTodp(value: Float): Float {
        val metrics = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics)
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)
        val request = Request.Builder()
            .header("Authorization", "Token "+ token)
            .url(BuildConfig.USER)
            .build()
        val response = OkHttpClient().newCall(request).execute()
        //println(response.body()!!.string())

        var jsonData = response.body()!!.string()
        amt = JSONObject(jsonData).getString("main_wallet")
        user.text = "Hi " + JSONObject(jsonData).getString("username") + "                      "
        balance.text="       Balance: $" + amt
        initData()
        curveChart.setDataList(mDataList)

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        title = "Total Wealth"
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (!MainActivity.isLogin) {
            menu!!.findItem(R.id.logged).isVisible = false
            menu.findItem(R.id.login).isVisible = true
           // menu.findItem(R.id.balance).isVisible = false
        } else {
            menu!!.findItem(R.id.logged).isVisible = true
            menu.findItem(R.id.login).isVisible = false
            //menu.findItem(R.id.balance).isVisible = true

        }
        val menuItem = menu.findItem(R.id.deposit)
        val rootView = menuItem.actionView

        amtShow = rootView.findViewById(R.id.balance_icon)
        amtShow.text = amt.split(".")[0]
        amtShow.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            android.R.id.home -> {
                // startActivity(Intent(this, MyAccount::class.java))
                onBackPressed()
                return true
            }
            R.id.login -> {
                startActivity(Intent(this, Login::class.java))
                return true
            }
            R.id.logged -> {
                startActivity(Intent(this, MyAccount::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }


    }
    private fun initData() {
        for (i in 0..5) {
            mPointArrayList.add(PointF(points[i][0], points[i][1]))
        }
        mCurveData.value = mPointArrayList
        mCurveData.color = Color.rgb(64,190,165)

        val drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue)
        mCurveData.drawable = drawable

        mCurveData.pointShape = PointShape.CIRCLE
        mCurveData.paintWidth = pxTodp(1f)
        mCurveData.textSize = pxTodp(10f)
        mDataList.add(mCurveData)
    }
}