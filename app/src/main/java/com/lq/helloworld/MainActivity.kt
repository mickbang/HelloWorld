package com.lq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    var task: MyAsyncTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            if (task == null) {
                task = MyAsyncTask()
                task?.execute("1")
            }
        }

        findViewById<Button>(R.id.btnEnd).setOnClickListener {
            task?.cancel(true)
            task = null
        }

    }
}