package com.lq.helloworld

import android.app.Application

class App:Application() {

    override fun onCreate() {
        super.onCreate()

        XLogManager.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()

        XLogManager.exit()
    }
}