package com.lq.helloworld

import android.util.Log
import com.lq.quene.BaseJob
import com.lq.quene.Result

private const val TAG = "LoggerTask"

class LoggerTask(private val text: String) : BaseJob(text.hashCode()) {

    override fun doJob(): Result {
        Log.d(TAG, "doJob: $text ==== ${Thread.currentThread().name}")
        return Result.SUCCESS
    }
}