package com.lq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lq.helloworld.databinding.ActivityMainBinding
import com.tencent.mars.xlog.Log

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            Log.d(TAG, "这是测试消息======》")
        }
    }
}