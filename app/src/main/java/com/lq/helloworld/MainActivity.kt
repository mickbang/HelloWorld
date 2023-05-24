package com.lq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Locale.getDefault()
    }
}