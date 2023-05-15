package com.lq.helloworld

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.text.format.DateUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.PermissionUtils
import java.text.SimpleDateFormat
import java.util.Calendar


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private var lm: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("系统的开始时间wei：${SystemClock.elapsedRealtime()}")


        if (GpsUtil.checkGps(this)) {
            // 获取位置信息
            // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER

            PermissionUtils.permission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
                .callback(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        lm = GpsUtil.getLocationManager(this@MainActivity, listener, locationListener)

                    }

                    override fun onDenied() {

                    }

                }).request()
        } else {
//            ToastUtil.centered(this, "请打开GPS设置")
            if (Build.VERSION.SDK_INT > 15) {
                val intent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                startActivity(intent)
            }
        }
    }

    // 状态监听
    @SuppressLint("MissingPermission")
    var listener = GpsStatus.Listener { event ->
        when (event) {
            GpsStatus.GPS_EVENT_FIRST_FIX -> {}
            GpsStatus.GPS_EVENT_SATELLITE_STATUS -> {
                // 获取当前状态
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return@Listener
                    }
                }
                val gpsStatus = lm!!.getGpsStatus(null)
                // 获取卫星颗数的默认最大值
                val maxSatellites = gpsStatus!!.maxSatellites
                // 创建一个迭代器保存所有卫星
                val iters: Iterator<GpsSatellite> = gpsStatus.satellites
                    .iterator()
                var count = 0
                while (iters.hasNext() && count <= maxSatellites) {
                    val s = iters.next()
                    count++
                }
//                tv_wzs.setText("搜索到：" + count + "颗卫星")
                println("搜索到：" + count + "颗卫星")
            }
            GpsStatus.GPS_EVENT_STARTED -> {}
            GpsStatus.GPS_EVENT_STOPPED -> {}
        }
    }

    // 位置监听
    private val locationListener: LocationListener = object : LocationListener {
        /**
         * 位置信息变化时触发
         */
        override fun onLocationChanged(location: Location) {
//            tv_zb.setText(
//                """
//                经度：${location.longitude}
//                经度：${location.latitude}
//                """.trimIndent()
//            )
            Log.i(TAG, "时间：" + location.time)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = location.time
            Log.i(TAG, "时间：" + SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(calendar.time))
            Log.i(TAG, "经度：" + location.longitude)
            Log.i(TAG, "纬度：" + location.latitude)
            Log.i(TAG, "海拔：" + location.altitude)
        }

        /**
         * GPS状态变化时触发
         */
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            when (status) {
                LocationProvider.AVAILABLE -> Log.i(TAG, "当前GPS状态为可见状态")
                LocationProvider.OUT_OF_SERVICE -> Log.i(TAG, "当前GPS状态为服务区外状态")
                LocationProvider.TEMPORARILY_UNAVAILABLE -> Log.i(TAG, "当前GPS状态为暂停服务状态")
            }
        }

        /**
         * GPS开启时触发
         */
        override fun onProviderEnabled(provider: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
            }
            val location: Location? = lm?.getLastKnownLocation(provider)
//            tv_zb.setText(location.longitude.toString() + "/n" + location.latitude)
        }

        /**
         * GPS禁用时触发
         */
        override fun onProviderDisabled(provider: String) {
//            tv_zb.setText("")
        }
    }

}