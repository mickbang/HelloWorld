package com.lq.core_logger;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class XLogger {
    private static String DEFAULT_TAG = "xlogger";
    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    private static Printer printer = new LoggerPrinter();

    private XLogger() {

    }

    public static void init() {

    }

    public static void addAdapter(LogAdapter adapter) {
        printer.addAdapter(adapter);
    }

    public static void clearLogAdapters() {
        printer.clearLogAdapters();
    }

    public static void d(@NonNull String tag, @Nullable String message) {
        printer.d(tag, message);
    }

    public static void d(@Nullable String message) {
        printer.d(DEFAULT_TAG, message);
    }


    public static void e(@NonNull String tag, @Nullable String message) {
        printer.e(tag, message);
    }

    public static void e(@Nullable String message) {
        printer.e(DEFAULT_TAG, message);
    }


    public static void e(@NonNull String tag, @Nullable Throwable throwable) {
        printer.e(tag, throwable);
    }

    public static void e(@Nullable Throwable throwable) {
        printer.e(DEFAULT_TAG, throwable);
    }


    public static void w(@NonNull String tag, @Nullable String message) {
        printer.w(tag, message);
    }

    public static void w(@Nullable String message) {
        printer.w(DEFAULT_TAG, message);
    }

    public static void i(@NonNull String tag, @Nullable String message) {
        printer.i(tag, message);
    }

    public static void i(@Nullable String message) {
        printer.i(DEFAULT_TAG, message);
    }

    public static void v(@NonNull String tag, @Nullable String message) {
        printer.v(tag, message);
    }

    public static void v(@Nullable String message) {
        printer.v(DEFAULT_TAG, message);
    }


    public static void a(@NonNull String tag, @Nullable String message) {
        printer.a(tag, message);
    }

    public static void a(@Nullable String message) {
        printer.a(DEFAULT_TAG, message);
    }


}
