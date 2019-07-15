package com.app.android.ibet.activity.UserProfile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.android.ibet.R
import com.app.android.ibet.activity.MainActivity
import com.app.android.ibet.activity.MainActivity.Companion.isLogin
import kotlinx.android.synthetic.main.activity_user_profile.*
import okhttp3.OkHttpClient
import okhttp3.Request
import android.graphics.PointF
import com.idtk.smallchart.data.CurveData
import com.idtk.smallchart.interfaces.iData.ICurveData
import com.idtk.smallchart.data.PointShape
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.util.TypedValue
import com.app.android.ibet.BuildConfig
import com.app.android.ibet.activity.Login.Login.Companion.token


class UserProfile : AppCompatActivity() {

    private val mDataList = ArrayList<ICurveData>()
    private val mCurveData = CurveData()
    private val mPointArrayList = ArrayList<PointF>()

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
        super.onCreate(savedInstanceState)
        println(token)

        val request = Request.Builder()
            .header("Authorization", "Token "+ token)
            .url(BuildConfig.USER)
            .build()
        val response = OkHttpClient().newCall(request).execute()
        println(response.body()!!.string())

        setContentView(R.layout.activity_user_profile)

        initData()
        curveChart.setDataList(mDataList)
        deposit.setOnClickListener {
            startActivity(Intent(this, Money::class.java))

        }
        withdraw.setOnClickListener {
            startActivity(Intent(this, Money::class.java))

        }
        open_bets.setOnClickListener {
            startActivity(Intent(this, Bets::class.java))
        }

        settled_bets.setOnClickListener {
            startActivity(Intent(this, Bets::class.java))
        }
        promotions.setOnClickListener {
            startActivity(Intent(this, Promotion::class.java))

        }
        settings.setOnClickListener {
            startActivity(Intent(this, Settings::class.java))

        }
        help.setOnClickListener {
            startActivity(Intent(this, Help::class.java))

        }
        responsible.setOnClickListener {
            startActivity(Intent(this, Responsible::class.java))

        }
        edit_profile.setOnClickListener {
            startActivity(Intent(this, Edit::class.java))

        }
        logout.setOnClickListener {
            isLogin = false
            token = ""
            startActivity(Intent(this, MainActivity::class.java))

        }
        change_password.setOnClickListener {
            startActivity(Intent(this, ChangePass::class.java))
        }
    }

    private fun initData() {
        for (i in 0..5) {
            mPointArrayList.add(PointF(points[i][0], points[i][1]))
        }
        mCurveData.value = mPointArrayList
        mCurveData.color = Color.rgb(64,190,165)

        val drawable = ContextCompat.getDrawable(this, R.drawable.fade_green)
        mCurveData.drawable = drawable

        mCurveData.pointShape = PointShape.CIRCLE
        mCurveData.paintWidth = pxTodp(1f)
        mCurveData.textSize = pxTodp(10f)
        mDataList.add(mCurveData)
    }

}