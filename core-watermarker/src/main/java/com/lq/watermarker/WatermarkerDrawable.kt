package com.lq.watermarker

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import kotlin.math.sqrt

class WatermarkerDrawable(val watermarkerConfig: WatermarkerConfig) : Drawable() {
    private val paint by lazy { Paint() }

    override fun draw(canvas: Canvas) {
        val width = bounds.right
        val height = bounds.bottom
        val diagonal = sqrt((width * width + height * height).toDouble()) // 对角线的长度

        paint.color = watermarkerConfig.waterTextColor
        paint.textSize =
            watermarkerConfig.waterTextSize // ConvertUtils.spToPx()这个方法是将sp转换成px，ConvertUtils这个工具类在我提供的demo里面有
        paint.isAntiAlias = true;
        val textWidth = paint.measureText(watermarkerConfig.waterText)
        canvas.drawColor(0x00000000)
        canvas.rotate(watermarkerConfig.waterTextRotation)

        var index = 0
        var fromX: Float
        // 以对角线的长度来做高度，这样可以保证竖屏和横屏整个屏幕都能布满水印
        var positionY = diagonal / 10f
        while (positionY <= diagonal) {
            fromX = -width + (index++ % 2) * textWidth; // 上下两行的X轴起始点不一样，错开显示
            var positionX = fromX
            while (positionX < width) {
                canvas.drawText(watermarkerConfig.waterText, positionX, positionY.toFloat(), paint)
                positionX += textWidth * 2
            }
            positionY += diagonal / 10
        }

        canvas.save()
        canvas.restore()
    }

    override fun setAlpha(alpha: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}