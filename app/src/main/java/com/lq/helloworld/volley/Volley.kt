package com.lq.helloworld.volley

object Volley {

    @JvmStatic
    fun newRequestQueue(): RequestQueue {
        val network = Network()
        return RequestQueue(network, 10).apply {
            start() } // 1000 is the number of requests to be queued
    }
}