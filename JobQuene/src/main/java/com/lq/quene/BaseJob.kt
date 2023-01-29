package com.lq.quene

import com.lq.quene.schedule.IJobScheduler

abstract class BaseJob(val jobId: Int) : IJob {
    private var jobPriority: JobPriority = JobPriority.DEFAULT
    private var jobStatus: JobStatus = JobStatus.PENDING
    private var jobSequence = 0

    override fun <T : IJobScheduler> enqueue(scheduler: T) {
        jobStatus = JobStatus.PENDING
        scheduler.add(this)
    }

    //提供给外部调用执行，这个可能在子线程执行，注意线程安全
    override fun run() {
        if (jobStatus != JobStatus.CANCEL) {
            jobStatus = JobStatus.RUNNING
            val result = doJob()
            jobStatus = if (result == Result.SUCCESS) {
                JobStatus.SUCCESS
            } else {
                JobStatus.ERROR
            }
        }
    }

    /**
     * 只能cancel pending状态下的job
     */
    override fun cancel(): Result {
        if (jobStatus == JobStatus.CANCEL || jobStatus == JobStatus.PENDING) {
            this.jobStatus = JobStatus.CANCEL
            return Result.SUCCESS
        }
        return Result.ERROR
    }

    override fun finish() {

    }

    fun isComplete(): Boolean {
        return this.jobStatus != JobStatus.PENDING &&
                this.jobStatus != JobStatus.RUNNING
    }

    override fun setSequence(sequence: Int) {
        this.jobSequence = sequence
    }

    override fun getSequence(): Int {
        return this.jobSequence
    }

    override fun setPriority(priority: JobPriority): IJob {
        this.jobPriority = priority
        return this
    }

    override fun getPriority(): JobPriority {
        return this.jobPriority
    }

    override fun getStatus(): JobStatus {
        return jobStatus
    }

    override fun compareTo(other: IJob): Int {
        val me = this.getPriority()
        val it = other.getPriority()
        return if (me == it) {
            getSequence() - other.getSequence()
        } else {
            it.ordinal - me.ordinal
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseJob) return false

        if (jobId != other.jobId) return false

        return true
    }

    override fun hashCode(): Int {
        return jobId
    }
}