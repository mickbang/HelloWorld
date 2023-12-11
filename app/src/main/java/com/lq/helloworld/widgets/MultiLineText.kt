package com.lq.helloworld.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetrics
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Xfermode
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.SizeUtils
import com.lq.helloworld.R

private const val text =
    "Class that describes the various metrics for a font at a given text size. Remember, Y values increase going down, so those values will be positive, and values that measure distances going up will be negative. This class is returned by getFontMetrics()."
private const val bitmapTopMargin = 130f
private const val TAG = "MultiLineText"

class MultiLineText(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            textSize = SizeUtils.dp2px(20f).toFloat()
        }
    private val fontMetrics = FontMetrics()
    private val bitmap = ImageUtils.getBitmap(R.mipmap.ic_launcher)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.getFontMetrics(fontMetrics)
        val bitmapLeft = (width - bitmap.width).toFloat()
        val bitmapTop = bitmapTopMargin
        val bitmapBottom = bitmapTopMargin + bitmap.height
        canvas?.drawBitmap(bitmap, bitmapLeft, bitmapTopMargin, paint)

        val measuredWidth1 = floatArrayOf(0f)
        var start = 0
        var count: Int
        var textVerOffset = -fontMetrics.top
        while (start < text.length) {
            val needSpace =
                textVerOffset + fontMetrics.bottom < bitmapTop || textVerOffset + fontMetrics.top > bitmapBottom
            val maxWith = if (!needSpace) (width.toFloat() - bitmap.width) else width.toFloat()
            count = paint.breakText(
                text,
                start,
                text.length,
                true,
                maxWith,
                measuredWidth1
            )
            Log.d(TAG, "onDraw: needSpace=$needSpace maxWith=$maxWith count=$count")

            canvas?.drawText(
                text,
                start,
                start + count,
                0f,
                textVerOffset,
                paint
            )
            start += count
            textVerOffset += paint.fontSpacing
        }


    }
}