package com.client.nettimesync

import android.os.AsyncTask
import android.os.SystemClock
import java.util.Date

object NtpTimeSyncManager {
    var ntpTime: Long? = null
    var ntpAsyncTask: NetTimeAsyncTask? = null
    var ntpTimeReference: Long? = null

    fun syncNtpTime() {
        if (ntpTime == null) {
            if (ntpAsyncTask != null && (ntpAsyncTask!!.status == AsyncTask.Status.PENDING || ntpAsyncTask!!.status == AsyncTask.Status.RUNNING)) {

            } else {
                ntpAsyncTask = NetTimeAsyncTask(this)
                ntpAsyncTask?.execute()
            }
        }
    }

    fun forceSyncNtp() {
        ntpAsyncTask?.cancel(true)
        ntpAsyncTask = NetTimeAsyncTask(this)
        ntpAsyncTask?.execute()
    }

    fun cacheNtpTime(ntpTime: Long) {
        ntpTimeReference = SystemClock.elapsedRealtime()
        this.ntpTime = ntpTime
    }

    fun getNtpSyncTimeOrNull(): Long? {
        if (ntpTime == null || ntpTimeReference == null) {
            return null
        }

        return ntpTime!! + SystemClock.elapsedRealtime() - ntpTimeReference!!
    }

    fun getNtpSyncDateOrNull(): Date? {
        val timeMill = getNtpSyncTimeOrNull()
        return if (timeMill == null) null else Date(timeMill!!)
    }

    fun getNtpSyncDateOrSystem(): Date {
        val timeMill = getNtpSyncTimeOrNull()
        return if (timeMill == null) Date() else Date(timeMill!!)
    }
}