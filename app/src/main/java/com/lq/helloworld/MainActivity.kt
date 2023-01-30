package com.lq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lq.quene.*
import com.lq.quene.schedule.JobSchedulerFactory

class MainActivity : AppCompatActivity() {
    private val scheduler by lazy {
        JobSchedulerFactory.getAsyncScheduler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn1).setOnClickListener {
            val task = LoggerTask("LOW1").apply {
                setPriority(JobPriority.LOW)
                enqueue(scheduler)
            }
            scheduler.remove(task)
        }
        findViewById<View>(R.id.btn2).setOnClickListener {
            LoggerTask("LOW")
                .setPriority(JobPriority.LOW)
                .enqueue(scheduler)
        }
        findViewById<View>(R.id.btn3).setOnClickListener {
            LoggerTask("HIGH")
                .setPriority(JobPriority.HIGH)
                .enqueue(scheduler)
        }
        findViewById<View>(R.id.btn4).setOnClickListener {
            LoggerTask("LOW")
                .setPriority(JobPriority.LOW)
                .enqueue(scheduler)
        }
    }
}