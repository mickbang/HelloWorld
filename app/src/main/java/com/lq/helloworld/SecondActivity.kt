package com.lq.helloworld

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lq.core_networklistener.NetListenerManager

class SecondActivity : AppCompatActivity() {

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, SecondActivity::class.java)
            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        NetListenerManager.ignore(this@SecondActivity::class.java)
        findViewById<Button>(R.id.btn).setOnClickListener {
            ThirdActivity.start(this@SecondActivity)
        }
    }
}