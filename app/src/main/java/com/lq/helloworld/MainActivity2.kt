package com.lq.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.client.nettimesync.NtpTimeSyncManager


private const val TAG = "MainActivity2"

class MainActivity2 : AppCompatActivity() {
    lateinit var btn: Button
    lateinit var sync: Button
    lateinit var textview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn = findViewById(R.id.btn)
        sync = findViewById(R.id.sync)
        textview = findViewById(R.id.tvTimeShow)

        btn.setOnClickListener {
            textview.text = "${textview.text}\n${NtpTimeSyncManager.getNtpSyncDateOrSystem()}"
        }

        sync.setOnClickListener {
            NtpTimeSyncManager.syncNtpTime()
        }


    }
}