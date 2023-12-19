package com.lq.helloworld

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dalvik.system.DexClassLoader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate1: ${Thread.currentThread().name}")
        GlobalScope.launch {
            Log.d(TAG, "onCreate2: ${Thread.currentThread().name}")
            runBlocking {
                launch {
                    delay(100)
                    Log.d(TAG, "onCreate3: ${Thread.currentThread().name}")
                }
                launch {
                    delay(100)
                    Log.d(TAG, "onCreate4: ${Thread.currentThread().name}")
                }

              val a =   async {

                }
                a.await()
            }

            Log.d(TAG, "onCreate6: ${Thread.currentThread().name}")


        }
        Log.d(TAG, "onCreate7: ${Thread.currentThread().name}")
    }

}