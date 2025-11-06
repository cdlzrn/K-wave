package com.cdlzrn.kwave

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
class KwaveApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Graph.provide(applicationContext)
    }
}