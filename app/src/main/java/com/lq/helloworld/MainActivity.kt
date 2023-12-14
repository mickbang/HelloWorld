package com.lq.helloworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val view = findViewById<TextView>(R.id.view)

//        view.setOnClickListener {
//            Log.d(TAG, "onCreate: ${window.decorView.parent}")
//            view.text = "11"
//            thread {
//                view.text = "${Thread.currentThread().name}"
//            }
//        }



    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }
}