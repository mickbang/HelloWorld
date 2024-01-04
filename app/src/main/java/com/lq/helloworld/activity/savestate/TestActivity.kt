package com.lq.helloworld.activity.savestate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import com.lq.helloworld.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val bundle = Bundle().apply {
            putString("param","liSi")
        }
        startActivity(Intent(this@TestActivity, SaveStateActivity::class.java).putExtras(bundle))
    }

    val lancher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

    }


}