package com.lq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lq.watermarker.Watermarker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onResume() {
        super.onResume()
        Watermarker.Builder()
            .build()
            .show(this)
    }
}