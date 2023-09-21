package com.lq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.util.Log

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {


    val thread1 = HandlerThreadCus()

    val thread2 = object : Thread("test2======>") {
        override fun run() {
            super.run()
            thread1.handler.obtainMessage(11).sendToTarget()
        }
    }

    val thread3: HandlerThread = HandlerThread("test3======>").apply {
        start()
    }

    val threadHandler = object : Handler(thread3.looper) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(TAG, "handleMessage: ${Thread.currentThread().name}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        threadHandler.sendEmptyMessage(111)
    }
}

class HandlerThreadCus : Thread("test1======>") {
    val handler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(TAG, "handleMessage: ${currentThread().name}")
        }
    }


    override fun run() {
        super.run()

    }
}

class ThreadCus : HandlerThread("test3======>") {

}