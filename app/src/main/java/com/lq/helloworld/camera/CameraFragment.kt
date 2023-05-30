package com.lq.helloworld.camera

import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.lq.helloworld.databinding.ActivityCameraBinding
import kotlin.math.abs

private const val TAG = "CameraFragment"

@Suppress("DEPRECATION")
class CameraFragment : Fragment(), SurfaceHolder.Callback {

    private val binding by lazy {
        ActivityCameraBinding.inflate(LayoutInflater.from(context))
    }

    private lateinit var surfaceHolder: SurfaceHolder
    private var camera: Camera? = null

    companion object {
        fun newInstance(): CameraFragment {
            return CameraFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        binding.apply {
            surfaceHolder = svCamera.holder
            surfaceHolder.addCallback(this@CameraFragment)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        camera = Camera.open()
        try {
            camera?.setPreviewDisplay(holder)
            setAutoFocus()
            camera?.startPreview()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setAutoFocus() {
        try {
            if (camera != null) {
                val params = camera!!.parameters
                // Check if autofocus mode is supported
                // Check if autofocus mode is supported
                val supportedFocusModes = params.supportedFocusModes
                if (supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    params.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
                    camera!!.parameters = params
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (holder.surface == null) {
            return
        }
        Log.d(TAG, "surfaceChanged: width=$width=====>height=$height")
        try {
            camera?.stopPreview()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        setAutoFocus()
        adaptScreen(width, height)
        setCameraDisplayOrientation()
        camera?.startPreview()
    }

    private fun adaptScreen(width: Int, height: Int) {
        val parameters = camera!!.parameters
        val supportedSizes = parameters.supportedPreviewSizes
        // Choose an optimal preview size based on the device's screen size
        val optimalSize = getOptimalPreviewSize(supportedSizes, width, height)
        if (optimalSize != null) {
            // Set the preview size
            parameters.setPreviewSize(optimalSize.width, optimalSize.height)
            // Update the camera parameters
            camera!!.parameters = parameters
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        camera?.stopPreview()
        camera?.release()
        camera = null
    }

    private fun setCameraDisplayOrientation() {
        val info = Camera.CameraInfo()
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info)
        val rotation: Int = requireActivity().windowManager.defaultDisplay.rotation
        var degrees = 0
        when (rotation) {
            Surface.ROTATION_0 -> degrees = 0
            Surface.ROTATION_90 -> degrees = 90
            Surface.ROTATION_180 -> degrees = 180
            Surface.ROTATION_270 -> degrees = 270
        }
        var result: Int
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360
            result = (360 - result) % 360 // compensate the mirror
        } else {
            result = (info.orientation - degrees + 360) % 360
        }
        camera!!.setDisplayOrientation(result)
    }


    private fun getOptimalPreviewSize(
        sizes: List<Camera.Size>?,
        screenWidth: Int,
        screenHeight: Int
    ): Camera.Size? {
        val ASPECT_TOLERANCE = 0.1
        val targetRatio = screenHeight.toDouble() / screenWidth
        if (sizes == null) return null
        var optimalSize: Camera.Size? = null
        var minDiff = Double.MAX_VALUE
        for (size in sizes) {
            val ratio = size.width.toDouble() / size.height
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue
            if (Math.abs(size.height - screenHeight) < minDiff) {
                optimalSize = size
                minDiff = Math.abs(size.height - screenHeight).toDouble()
            }
        }
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE
            for (size in sizes) {
                if (Math.abs(size.height - screenHeight) < minDiff) {
                    optimalSize = size
                    minDiff = abs(size.height - screenHeight).toDouble()
                }
            }
        }
        return optimalSize
    }


}