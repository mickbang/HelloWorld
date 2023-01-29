package com.lq.quene

import com.lq.quene.queue.JobTaskQueue
import com.lq.quene.schedule.BaseJobScheduler


class SyncJobScheduler : BaseJobScheduler() {
    private val queue: JobTaskQueue by lazy {
        JobTaskQueue()
    }

    init {
        while (true) {
            if (!isStop) {
                queue.poll()?.run()
            }
        }
    }

    override fun destroy() {
        stop()
    }

    override fun add(job: BaseJob) {
        if (isStop) {
            start()
        }
        queue.add(job)
    }

    override fun remove(job: BaseJob) {
        queue.remove(job)
    }
}