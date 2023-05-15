package com.client.nettimesync

import android.os.AsyncTask
import android.os.SystemClock

import java.util.*


@Suppress("DEPRECATION")
class NetTimeAsyncTask(val manager: NtpTimeSyncManager) : AsyncTask<Void, Void, Long?>() {

    val ntpServer = "pool.ntp.org"
    override fun doInBackground(vararg params: Void?): Long? {
        println("开始同步")
        val sntpClient = SntpClient()
        if (sntpClient.requestTime(ntpServer, 30000)) {
            val now =
                sntpClient.ntpTime + SystemClock.elapsedRealtime() - sntpClient.ntpTimeReference
            return now
        }
        return null
    }


    override fun onPostExecute(result: Long?) {
        super.onPostExecute(result)
        if (result != null) {
            println("同步成功current${Date(result)}")
            manager.cacheNtpTime(result)
        }else{
            println("同步失败current")
        }
    }
}