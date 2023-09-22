package com.lq.core_logger.use;

import android.content.Context;

import com.lq.core_logger.XLogger;
import com.lq.core_logger.use.logger.LoggerAdapter;

public class LoggerManager {

    public static void init(Context context){
        XLogger.init();
        XLogger.addAdapter(new LoggerAdapter(context));
    }
}
