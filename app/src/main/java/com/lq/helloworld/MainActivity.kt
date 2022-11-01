package com.lq.helloworld

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.lq.helloworld.camerax.CameraxActivity
import com.lq.helloworld.databinding.ActivityMainBinding
import java.lang.Integer.min
import java.lang.Math.abs
import java.lang.Math.max
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.cameraxButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, CameraxActivity::class.java))
        }

        viewBinding.camera2Button.setOnClickListener {

        }
    }
}