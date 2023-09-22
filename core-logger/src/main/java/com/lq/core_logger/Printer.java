package com.lq.core_logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Printer {
    void addAdapter(@NonNull LogAdapter adapter);

    void d(@NonNull String tag, @Nullable String message);

    void e(@NonNull String tag, @Nullable String message);

    void e(@NonNull String tag, @Nullable Throwable throwable);

    void w(@NonNull String tag, @Nullable String message);

    void i(@NonNull String tag, @Nullable String message);

    void v(@NonNull String tag, @Nullable String message);

    void a(@NonNull String tag, @Nullable String message);

    void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable);

    void clearLogAdapters();

}
