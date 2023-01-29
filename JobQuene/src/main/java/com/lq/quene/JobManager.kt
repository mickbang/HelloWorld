package com.lq.quene

import com.lq.quene.schedule.IJobScheduler

open class JobManager(val scheduler: IJobScheduler) : IJobScheduler {
    override fun start() {
        scheduler.start()
    }

    override fun stop() {
        scheduler.stop()
    }

    override fun destroy() {
        scheduler.destroy()
    }

    override fun add(job: BaseJob) {
        scheduler.add(job)
    }

    override fun remove(job: BaseJob) {
        job.cancel()
        scheduler.remove(job)
    }
}