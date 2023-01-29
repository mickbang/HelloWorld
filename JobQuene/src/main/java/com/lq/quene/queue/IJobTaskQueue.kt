package com.lq.quene.queue

import com.lq.quene.IJob

interface IJobTaskQueue {
    fun <T : IJob> add(task: T): Int
    fun <T : IJob> remove(task: T)
    fun poll(): IJob?
    fun take(): IJob?
    fun clear()
    fun size(): Int
}