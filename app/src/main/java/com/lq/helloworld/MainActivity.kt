package com.lq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lq.quene.*
import com.lq.quene.schedule.JobSchedulerFactory

class MainActivity : AppCompatActivity() {
    private val scheduler by lazy {
        JobSchedulerFactory.getMainScheduler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn1).setOnClickListener {
            LoggerTask("LOW1")
                .setPriority(JobPriority.LOW)
                .enqueue(scheduler)
            LoggerTask("LOW2")
                .setPriority(JobPriority.LOW)
                .enqueue(scheduler)
            LoggerTask("DEFAULT")
                .setPriority(JobPriority.DEFAULT)
                .enqueue(scheduler)
            LoggerTask("HIGH")
                .setPriority(JobPriority.HIGH)
                .enqueue(scheduler)
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