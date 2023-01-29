package com.lq.helloworld

import com.lq.quene.JobManager
import com.lq.quene.SyncJobScheduler

class TestJobQueue : JobManager(SyncJobScheduler()) {
    
}