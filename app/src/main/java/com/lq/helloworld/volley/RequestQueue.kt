package com.lq.helloworld.volley

import java.util.concurrent.PriorityBlockingQueue

class RequestQueue(private val network: Network, private val poolSize: Int) {

    private val currents = HashSet<Request>()
    private val networkQueue = PriorityBlockingQueue<Request>()

    private var dispatchers: Array<NetworkDispatcher?> = arrayOfNulls(poolSize)

    fun start() {
        stop()
        for (i in 0 until poolSize) {
            dispatchers[i] = NetworkDispatcher(network, networkQueue).also { it.start() }
        }
    }

    fun stop() {
        dispatchers.forEach { it?.quit() }
    }

    fun add(request: Request) {
        networkQueue.add(request)
    }

    fun cancelAll(filter: (request: Request) -> Boolean) {
        currents.forEach {
            if (filter.invoke(it)) {
                it.cancel()
            }
        }
    }


}