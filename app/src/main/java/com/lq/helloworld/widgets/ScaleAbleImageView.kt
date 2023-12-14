package com.lq.helloworld.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ScaleGestureDetectorCompat
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.SizeUtils
import com.lq.helloworld.R

const val IMAGE_SIZE = 200F

class ScaleAbleImageView(context: Context, attrs: AttributeSet?) : View(context, attrs),
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private val bitmap by lazy {
        ImageUtils.scale(
            ImageUtils.getBitmap(R.mipmap.ic_launcher),
            SizeUtils.dp2px(IMAGE_SIZE),
            SizeUtils.dp2px(IMAGE_SIZE)
        )
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

    }

    private var imageOffsetX: Float = 0f
    private var imageOffsetY: Float = 0f
    private val gestureDetector = GestureDetectorCompat(context,this).apply {
        setOnDoubleTapListener(this@ScaleAbleImageView)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        imageOffsetX =
            (w - SizeUtils.dp2px(IMAGE_SIZE)) / 2F

        imageOffsetY =
            (h - SizeUtils.dp2px(IMAGE_SIZE)) / 2F

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawBitmap(bitmap, imageOffsetX, imageOffsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }
}