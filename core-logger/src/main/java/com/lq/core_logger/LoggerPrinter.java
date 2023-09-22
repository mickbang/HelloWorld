package com.lq.core_logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LoggerPrinter implements Printer {
    private final List<LogAdapter> logAdapters = new ArrayList<>();

    @Override
    public void addAdapter(@NonNull LogAdapter adapter) {
        logAdapters.add(adapter);
    }

    @Override
    public void d(@NonNull String tag, @Nullable String message) {
        log(XLogger.DEBUG, tag, message, null);
    }

    @Override
    public void e(@NonNull String tag, @Nullable String message) {
        log(XLogger.INFO, tag, message, null);
    }

    @Override
    public void e(@NonNull String tag, @Nullable Throwable throwable) {
        log(XLogger.ERROR, tag, null, throwable);
    }

    @Override
    public void w(@NonNull String tag, @Nullable String message) {
        log(XLogger.WARN, tag, message, null);
    }

    @Override
    public void i(@NonNull String tag, @Nullable String message) {
        log(XLogger.INFO, tag, message, null);
    }

    @Override
    public void v(@NonNull String tag, @Nullable String message) {
        log(XLogger.VERBOSE, tag, message, null);
    }

    @Override
    public void a(@NonNull String tag, @Nullable String message) {
        log(XLogger.ASSERT, tag, message, null);
    }

    @Override
    public synchronized void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        for (LogAdapter adapter : logAdapters) {
            if (adapter.isLoggable(priority, tag)) {
                adapter.log(priority, tag, message, throwable);
            }
        }
    }

    @Override
    public void clearLogAdapters() {
        logAdapters.clear();
    }
}
