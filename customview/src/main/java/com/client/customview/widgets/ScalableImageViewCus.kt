package com.client.customview.widgets

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.animation.doOnEnd
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.blankj.utilcode.util.ImageUtils
import com.client.common.dp
import com.client.customview.R
import kotlin.math.max
import kotlin.math.min

private const val TAG = "ScalableImageViewCus"

class ScalableImageViewCus(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val IMAGE_SIZE = 100.dp
    private val bitmap = ImageUtils.getBitmap(R.drawable.avator, IMAGE_SIZE, IMAGE_SIZE)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val scalableImageViewGesture = ScalableImageViewGesture()
    private val gesture = GestureDetectorCompat(context, scalableImageViewGesture)
    private val scalableImageViewScaleGesture = ScalableImageViewScaleGesture()
    private val scaleGestureDetector = ScaleGestureDetector(context, scalableImageViewScaleGesture)

    private var bigScale = 0f
    private var smallScale = 0f
    private var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var big = false

    private lateinit var animator: ObjectAnimator

    private var offsetX = 0f
    private var offsetY = 0f
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private val scroller = OverScroller(context)

    private val flingRunnable = FlingRunnable()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            resolveSize(bitmap.width, widthMeasureSpec),
            resolveSize(bitmap.height, heightMeasureSpec)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width - bitmap.width) / 2f
        originalOffsetY = (height - bitmap.height) / 2f

        if (bitmap.width / bitmap.height.toFloat() > width / height) {
            smallScale = width / bitmap.width.toFloat()
            bigScale = height / bitmap.height.toFloat()
        } else {
            bigScale = width / bitmap.width.toFloat()
            smallScale = height / bitmap.height.toFloat()
        }

        currentScale = smallScale
        animator =
            ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale).apply {
                doOnEnd {
                    if (!big) {
                        offsetX = 0f
                        offsetY = 0f
                    }
                }
            }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        val scaleF = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleF, offsetY * scaleF)
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(
            bitmap,
            originalOffsetX,
            originalOffsetY,
            paint
        )
        canvas.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result = scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            gesture.onTouchEvent(event)
        }
        return result
    }


    inner class FlingRunnable : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@ScalableImageViewCus, this@FlingRunnable)
            }
        }
    }

    inner class ScalableImageViewGesture : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (big) {
                offsetX -= distanceX
                offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2f)
                offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2f)
                offsetY -= distanceY
                offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2f)
                offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2f)
                invalidate()
            }
            return false
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (big) {
                scroller.fling(
                    offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    (-(bitmap.width * bigScale - width) / 2f).toInt(),
                    ((bitmap.width * bigScale - width) / 2f).toInt(),
                    (-(bitmap.height * bigScale - height) / 2f).toInt(),
                    ((bitmap.height * bigScale - height) / 2f).toInt(),
                    40.dp,
                    40.dp
                )
                ViewCompat.postOnAnimation(this@ScalableImageViewCus, flingRunnable)
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            big = !big
            if (big) {
                animator.start()
            } else {
                animator.reverse()
            }
            return false
        }
    }

    inner class ScalableImageViewScaleGesture :
        ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scale = currentScale * detector.scaleFactor
            if (scale in smallScale..bigScale) {
                currentScale *= detector.scaleFactor
                return true
            }
            return false
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return true
        }
    }
}