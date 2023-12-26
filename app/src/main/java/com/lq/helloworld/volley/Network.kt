package com.lq.helloworld.volley

import android.util.Log

private const val TAG = "Network"
class Network {
    fun processRequest(request: Request) {
        Thread.sleep(2_000)
        Log.d(TAG, "processRequest: ${request.requestParam}=========currentThread:${Thread.currentThread().name}")
    }

}