package com.lq.watermarker

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

class Watermarker private constructor(
    val watermarkerConfig: WatermarkerConfig
) {
    fun show(activity: Activity) {
        if (activity.findViewById<View?>(R.id.watermarker_container_id) == null) {
            val watermarkerDrawable = WatermarkerDrawable(watermarkerConfig)
            val rootView: ViewGroup? = activity.window.decorView as ViewGroup?
            val layout = FrameLayout(activity)
            layout.id = R.id.watermarker_container_id
            layout.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            layout.background = watermarkerDrawable
            rootView?.addView(layout)
        }
    }

    class Builder {
        var waterText: String = "TEST"
            private set
        var waterTextColor: Int = Color.parseColor("#4DAEAEAE")
            private set
        var waterTextSize: Float = 40f
            private set
        var waterTextRotation: Float = -25f
            private set

        fun setWaterText(waterText: String) = apply {
            this.waterText = waterText
        }

        fun setWaterTextColor(waterTextColor: Int) = apply {
            this.waterTextColor = waterTextColor
        }

        fun setWaterTextSize(waterTextSize: Float) = apply {
            this.waterTextSize = waterTextSize
        }

        fun setWaterRotation(waterTextRotation: Float) = apply {
            this.waterTextRotation = waterTextRotation
        }

        fun build(): Watermarker {
            return Watermarker(
                WatermarkerConfig(
                    waterText,
                    waterTextColor,
                    waterTextSize,
                    waterTextRotation
                )
            )
        }
    }
}