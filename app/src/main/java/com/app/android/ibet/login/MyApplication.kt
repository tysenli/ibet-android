package com.app.android.ibet.login


import android.app.Application
import com.facebook.*
import com.facebook.appevents.AppEventsLogger



class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }


}
