package com.lq.helloworld.camerax

import android.Manifest
import android.content.ContentValues
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
import com.lq.helloworld.LumaListener
import com.lq.helloworld.LuminosityAnalyzer
import com.lq.helloworld.databinding.ActivityCameraxBinding
import com.lq.helloworld.databinding.ActivityMainBinding
import java.lang.Integer.min
import java.lang.Math.abs
import java.lang.Math.max
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraxActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityCameraxBinding

    private lateinit var cameraExecutor: ExecutorService

    private var imageCapture: ImageCapture? = null

    private var imageAnalyzer: ImageAnalysis? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCameraxBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        viewBinding.imageCaptureButton.setOnClickListener { takePhoto() }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValue = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOption = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValue
            )
            .build()

        imageCapture.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }

                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

            })
    }

    private fun startCamera() {
        // Used to bind the lifecycle of cameras to the lifecycle owner
        //1.创建ProcessCameraProvider
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({

            val metrics = windowManager.defaultDisplay
            Log.d(TAG, "Screen metrics: ${metrics.width} x ${metrics.height}")

            val screenAspectRatio = aspectRatio(metrics.width, metrics.height)
            Log.d(TAG, "Preview aspect ratio: $screenAspectRatio")

            val rotation =
                (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            //2.配置preview
            val preview = Preview.Builder()
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
                }

            // Select back camera as a default
            //3.选择默认开启的摄像头
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            imageCapture = ImageCapture.Builder()
                .setTargetAspectRatio(screenAspectRatio)
                .setTargetRotation(rotation)
                .build()


            imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer(object : LumaListener {
                        override fun listener(luma: Double) {
                            Log.d(TAG, "Average luminosity: $luma")
                        }
                    }))
                }

            //4.绑定usecase
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture,
                    imageAnalyzer
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }


    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}