package com.lq.quene.schedule

import com.lq.quene.BaseJob

interface IJobScheduler {
    fun start()
    fun stop()
    fun destroy()
    fun add(job: BaseJob)
    fun remove(job: BaseJob)
}