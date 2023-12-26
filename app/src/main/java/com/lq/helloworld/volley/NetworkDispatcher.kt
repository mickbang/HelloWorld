package com.lq.helloworld.volley

import java.util.concurrent.BlockingQueue

class NetworkDispatcher(private val network: Network, private val queue: BlockingQueue<Request>) : Thread() {
    private var isQuit = false
    override fun run() {
        super.run()
        while (!isQuit){
            try {
                val request = queue.take()
                if (request != null) {
                    if (!request.isCanceled) {
                        network.processRequest(request)
                    }
                }
            }catch (e:InterruptedException){
                if (isQuit) {
                    Thread.currentThread().interrupt()
                    return
                }
            }
        }
    }


    fun quit() {
        isQuit = true
        interrupt()
    }

}