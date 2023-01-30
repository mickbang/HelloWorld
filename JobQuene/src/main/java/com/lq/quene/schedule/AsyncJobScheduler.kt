package com.lq.quene.schedule

import android.os.AsyncTask
import android.util.Log
import com.lq.quene.BaseJob
import com.lq.quene.queue.BlockJobTaskQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock

private const val TAG = "AsyncJobScheduler"

@Suppress("DEPRECATION")
class AsyncJobScheduler(val pvdId: String) : AsyncTask<String, Int, Boolean>(), IJobScheduler {

    private val lock by lazy {
        ReentrantLock()
    }

    private val executor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    private val queue: BlockJobTaskQueue by lazy {
        BlockJobTaskQueue()
    }

    override fun onPreExecute() {
        super.onPreExecute()

    }

    /**
     * take和remove可能同时进行，ke换成非阻塞队列加锁处理
     */
    override fun doInBackground(vararg p0: String?): Boolean {
        while (true) {
            val job = queue.take()
            if (job != null) {
                job.run()
            }
        }
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
    }

    override fun start() {
        executeOnExecutor(executor, pvdId)
    }

    override fun stop() {
        cancel(true)
    }

    override fun destroy() {

    }

    override fun add(job: BaseJob) {
        lock.lock()
        try {
            queue.add(job)
            if (status == Status.PENDING) {
                start()
            }
        } finally {
            lock.unlock()
        }
    }

    override fun remove(job: BaseJob) {
        lock.lock()
        try {
            queue.remove(job)
        } finally {
            lock.unlock()
        }
    }
}