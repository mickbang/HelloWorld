package com.lq.quene.queue

import com.lq.quene.IJob
import java.util.concurrent.BlockingQueue
import java.util.concurrent.PriorityBlockingQueue

class BlockJobTaskQueue : IJobTaskQueue {

    private val taskQueue: BlockingQueue<IJob> by lazy {
        PriorityBlockingQueue()
    }

    override fun <T : IJob> add(task: T): Int {
        if (!taskQueue.contains(task)) {
            taskQueue.add(task)
        }
        return taskQueue.size
    }

    override fun <T : IJob> remove(task: T) {
        if (taskQueue.contains(task)) {
            taskQueue.remove(task)
        }
    }

    override fun poll(): IJob {
        return taskQueue.poll()
    }

    override fun take(): IJob {
        return taskQueue.take()
    }

    override fun clear() {
        taskQueue.clear()
    }

    override fun size(): Int {
        return taskQueue.size
    }
}