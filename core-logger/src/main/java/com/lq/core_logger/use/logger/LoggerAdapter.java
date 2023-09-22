package com.lq.core_logger.use.logger;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lq.core_logger.BuildConfig;
import com.lq.core_logger.LogAdapter;
import com.lq.core_logger.XLogger;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


public class LoggerAdapter extends LogAdapter {

    public LoggerAdapter(Context context) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Skips some method invokes in stack trace. Default 5
//        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My custom tag")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        FormatStrategy csvformatStrategy = CsvFormatStrategy1.newBuilder()
                .tag("custom")
                .logPath(context.getExternalFilesDir(null).getAbsolutePath())
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(csvformatStrategy));
    }


    @Override
    public int getLogLevel() {
        return BuildConfig.DEBUG ? XLogger.DEBUG : XLogger.INFO;
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message, @Nullable Throwable throwable) {
        Logger.log(priority, tag, message, throwable);
    }
}
