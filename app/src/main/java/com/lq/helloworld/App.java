package com.lq.helloworld;

import android.app.Application;

import com.lq.core_logger.use.LoggerManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LoggerManager.init(this);
    }
}
