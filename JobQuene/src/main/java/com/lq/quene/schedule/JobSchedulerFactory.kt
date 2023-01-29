package com.lq.quene.schedule

import com.lq.quene.JobScheduler
import com.lq.quene.ThreadSchedulers


object JobSchedulerFactory {
    @JvmStatic
    fun getMainScheduler(): JobScheduler {
        return JobScheduler(threadScheduler = ThreadSchedulers.MAIN)
    }

    @JvmStatic
    fun getIoScheduler(): JobScheduler {
        return JobScheduler(threadScheduler = ThreadSchedulers.IO)
    }
}