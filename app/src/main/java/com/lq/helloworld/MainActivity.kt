package com.lq.helloworld

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.dynamsoft.dbr.BarcodeReader
import com.dynamsoft.dbr.ImageData
import com.dynamsoft.dbr.TextResult
import com.dynamsoft.dbr.TextResultListener
import com.dynamsoft.dce.CameraEnhancer
import com.dynamsoft.dce.CameraEnhancerException
import com.lq.helloworld.databinding.ActivityMainBinding


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), TextResultListener {
    private val binding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this@MainActivity))
    }
    private val mCameraEnhancer by lazy {
        CameraEnhancer(this@MainActivity)
    }
    private val mBarcodeReader by lazy {
        BarcodeReader()
    }

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mCameraEnhancer.cameraView = binding.cameraView
//        binding.cameraView.overlayVisible = true
//        binding.cameraView.removeOverlay()
        mBarcodeReader.setCameraEnhancer(mCameraEnhancer)

        BarcodeReader.initLicense(
            ""
        ) { p0, p1 ->
            if (p0) {
                Log.d(TAG, "onCreate: init success")
            } else {
                Log.d(TAG, "onCreate: init fail ====>fail reason:${p1.message}")
            }

        }
        mBarcodeReader.setTextResultListener(this)
    }


    override fun onResume() {
        super.onResume()
        try {
            mCameraEnhancer.open()
            mBarcodeReader.startScanning()
        } catch (e: CameraEnhancerException) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            mCameraEnhancer.close()
            mBarcodeReader.stopScanning()
        } catch (e: CameraEnhancerException) {
            e.printStackTrace()
        }
    }

    var sameCount = 1
    var lastResult = ""
    override fun textResultCallback(p0: Int, p1: ImageData?, p2: Array<out TextResult>?) {
        if (!p2.isNullOrEmpty()) {
            val ss = java.lang.StringBuilder()
            p2.forEach {
                ss.append(it.barcodeText + "\n")
            }
            Log.d(TAG, "textResultCallback: $ss")
           runOnUiThread {
               try {
                   if (lastResult == ss.toString()) {
                       sameCount++
                       binding.tvResult.text = "$sameCount \n$ss"
                   } else {
                       sameCount = 1
                       lastResult = ss.toString()
                       binding.tvResult.text = ss
                   }
               }catch (e:Exception){
                   e.printStackTrace()
               }
           }
        }
    }
}