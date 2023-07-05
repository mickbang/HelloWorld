package com.lq.helloworld

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.lq.helloworld.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<MainViewModel>()
    val binding by DataBindingDelegate<ActivityMainBinding>(R.layout.activity_main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        globalSc()

        lifecycleScope.launch {
            launch(Dispatchers.IO) {
                (1..5).asFlow()
                    .filter {
                        Log.d(TAG, "onCreate: ${Thread.currentThread().name}")
                        it % 2 == 1
                    }
                    .collect{
                        Log.d(TAG, "onCreate: ${Thread.currentThread().name}====>$it")
                    }
            }


        }



    }


    fun globalSc() {
        GlobalScope.launch {
            runBlocking {
                launch(Dispatchers.IO) {
                    delay(100)
                    Log.d(TAG, "globalSc: ${Thread.currentThread().name} ===launch")
                }

                coroutineScope {
                    delay(500)
                    Log.d(TAG, "globalSc: ${Thread.currentThread().name} ===coroutineScope")
                }
                delay(50)
                Log.d(TAG, "globalSc: ${Thread.currentThread().name} ===runBlocking")
            }

        }
        lifecycle.coroutineScope.launch {
            //Do Something
        }

    }
}