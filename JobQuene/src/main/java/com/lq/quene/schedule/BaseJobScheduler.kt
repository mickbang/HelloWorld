package com.lq.quene.schedule


abstract class BaseJobScheduler : IJobScheduler {
    var isStop = false
    override fun start() {
        isStop = false
    }

    override fun stop() {
        isStop = true
    }

}