package com.lq.quene

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.lq.quene.queue.BlockJobTaskQueue
import com.lq.quene.schedule.BaseJobScheduler
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

private const val TAG = "JobScheduler"

class JobScheduler(val threadScheduler: ThreadSchedulers = ThreadSchedulers.MAIN) :
    BaseJobScheduler() {
    private val mLock by lazy {
        ReentrantLock()
    }
    private var mCondition: Condition = mLock.newCondition()

    private val queue: BlockJobTaskQueue by lazy {
        BlockJobTaskQueue()
    }
    private val jobHandler: JobHandler by lazy {
        JobHandler()
    }

    private val workThread = object : Thread() {
        override fun run() {
            super.run()
            Log.d(TAG, "run: 线程开始执行====")
            while (true) {
                Log.d(TAG, "run: 循环开始执行====")
                mLock.lock()
                try {
                    while (queue.size() == 0) {
                        mCondition.await()
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    mLock.unlock()
                }
                if (!isStop) {
                    val task = queue.take()
                    if (threadScheduler == ThreadSchedulers.IO) {
                        task.run()
                    } else {
                        jobHandler.obtainMessage(0, JobInf(task)).sendToTarget()
                    }
                }
            }
        }
    }


    init {
        workThread.start()
    }

    override fun destroy() {

    }

    override fun add(job: BaseJob) {
        if (isStop) {
            start()
        }
        mLock.lock()
        try {
            queue.add(job)
            mCondition.signal()
        } finally {
            mLock.unlock()
        }
    }

    override fun remove(job: BaseJob) {
        mLock.lock()
        try {
            queue.remove(job)
            mCondition.signal()
        } finally {
            mLock.unlock()
        }
    }


    inner class JobHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val what = msg.what
            if (what == 0) {
                if (!isStop) {
                    val jobInf = msg.obj as JobInf
                    jobInf.job.run()
                }
            }
        }
    }
}

data class JobInf(val job: IJob)
