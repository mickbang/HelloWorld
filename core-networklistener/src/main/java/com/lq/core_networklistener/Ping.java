package com.lq.core_networklistener;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Ping extends AsyncTask<Context, Void, Context> {
    private Ping_navigator cb;
    private int mExitValue = 0;
    private Thread activeThread;

    private List<String> ips = new ArrayList<>();
    int currentIpIndex = 0;

    Ping() {
        ips.add("www.apple.com");
        ips.add("www.google.com");
        ips.add("www.baidu.com");
        currentIpIndex = 0;
    }

    @Override
    protected Context doInBackground(Context... contexts) {
        try {
            activeThread = Thread.currentThread();
            Thread.sleep(ConnectionHandler.getDelay());
            pingProcess();
        } catch (InterruptedException e) {
            if (currentIpIndex != 0) {
                currentIpIndex = currentIpIndex - 1;
            }
            pingProcess();
            if (ConnectionHandler.isLogsEnabled())
                System.out.println("Ping Exception:" + e.getMessage());
        }

        return contexts[0];
    }

    @Override
    protected void onPostExecute(Context context) {
        if (mExitValue == 0) {
            cb.replied(context);

        } else {
            cb.timeout(context);

        }
        cb.ended(context);
        super.onPostExecute(context);
    }

    private void pingProcess() {
        pingProcess(getIp());
        while (mExitValue != 0 && currentIpIndex != ips.size() - 1) {
            pingProcess(getIp());
        }
    }

    private String getIp() {
        currentIpIndex = currentIpIndex < ips.size() ? currentIpIndex : ips.size() - 1;
        String result = ips.get(currentIpIndex);
        currentIpIndex++;
        return result;
    }

    private void pingProcess(String ip) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 -i 1 -W 1 " + ip);/////////////
            mExitValue = mIpAddrProcess.waitFor();
            if (ConnectionHandler.isLogsEnabled())
                System.out.println(" Ping mExitValue " + mExitValue);


        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            if (ConnectionHandler.isLogsEnabled())
                System.out.println("Ping Exception:" + e);

        }
    }

    public Ping(Ping_navigator cb) {
        this.cb = cb;
    }

    Ping setCb(Ping_navigator cb) {
        this.cb = cb;
        return this;
    }

    void resume() {
        activeThread.interrupt();
    }

}