package com.lq.quene

import com.lq.quene.schedule.IJobScheduler

interface IJob : Comparable<IJob>, Runnable {
    fun <T : IJobScheduler> enqueue(scheduler: T)
    fun doJob(): Result
    fun finish()
    fun cancel(): Result
    fun setPriority(priority: JobPriority):IJob
    fun getPriority(): JobPriority
    fun getStatus(): JobStatus

    //当优先级相同 按照插入顺序 先入先出 该方法用来标记插入顺序
    fun setSequence(mSequence: Int)

    //获取入队次序
    fun getSequence(): Int
}