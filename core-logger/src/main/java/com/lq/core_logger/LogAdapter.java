package com.lq.core_logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class LogAdapter {

    public abstract int getLogLevel();

    public boolean isLoggable(int priority, @Nullable String tag) {
        return getLogLevel() <= priority;
    }

    /**
     * Each log will use this pipeline
     *
     * @param priority is the log level e.g. DEBUG, WARNING
     * @param tag      is the given tag for the log message.
     * @param message  is the given message for the log message.
     */
    protected abstract void log(int priority, @Nullable String tag, @NonNull String message, @Nullable Throwable throwable);
}
