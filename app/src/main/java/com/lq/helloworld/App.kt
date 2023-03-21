package com.lq.helloworld

import android.app.Application
import com.lq.core_networklistener.NetListenerManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        NetListenerManager.init(this)
    }
}