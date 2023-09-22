package com.lq.helloworld;

import android.content.Context;
import android.os.Environment;

import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

public class XLogManager {
    final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    final String logPath = SDCARD + "/marssample/log";
    static final String logFileName = "temp";

    public static void init(Context context) {
        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");
        final String SDCARD = context.getExternalFilesDir("").getAbsolutePath();
        final String logPath = SDCARD + "/marssample/log";
// this is necessary, or may crash for SIGBUS
        final String cachePath = context.getFilesDir() + "/xlog";
//init xlog
        Xlog.XLogConfig logConfig = new Xlog.XLogConfig();
        logConfig.mode = Xlog.AppednerModeAsync;
        logConfig.logdir = logPath;
        logConfig.nameprefix = logFileName;
        logConfig.pubkey = "";
        logConfig.compressmode = Xlog.ZLIB_MODE;
        logConfig.compresslevel = 0;
        logConfig.cachedir = "";
        logConfig.cachedays = 0;

        Xlog xlog = new Xlog();
        Log.setLogImp(xlog);
        Log.setConsoleLogOpen(true);
        Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "LOGSAMPLE", 0);
    }

    public static void exit(){
        Log.appenderClose();
    }

}
