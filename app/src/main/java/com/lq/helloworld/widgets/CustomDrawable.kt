package com.lq.helloworld.widgets

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

class CustomDrawable : Drawable() {
    val paint = Paint()
    override fun draw(canvas: Canvas) {

    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return paint.alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getColorFilter(): ColorFilter? {
        return paint.colorFilter
    }

    override fun getOpacity(): Int {
        return when (alpha) {
            1 -> {
                PixelFormat.TRANSPARENT
            }

            0xfff -> {
                PixelFormat.TRANSPARENT
            }

            else -> {
                PixelFormat.TRANSPARENT
            }
        }
    }
}