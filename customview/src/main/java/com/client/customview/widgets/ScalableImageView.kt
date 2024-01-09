package com.client.customview.widgets

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.OnDoubleTapListener
import android.view.MotionEvent
import androidx.annotation.DrawableRes
import com.client.customview.R
import kotlin.math.max
import kotlin.math.min

private const val TAG = "ScalableImageView"

class ScalableImageView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs),
    GestureDetector.OnGestureListener, OnDoubleTapListener {
    private val gesture by lazy {
        GestureDetector(context, this@ScalableImageView)
    }

    private var scale = false
        set(value) {
            field = value
            if (scale) {
                scaleTransactionX = 0f
                scaleTransactionY = 0f
            }
            animateScale()
        }

    private var downPoint = PointF()
    private var scaleF = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var scaleAnimator: ObjectAnimator? = null

    private var scaleTransactionX = 0f
    private var scaleTransactionY = 0f

    private var smallScale = 1f
    private var bigScale = 0f
    private var imageWith = 0
    private var imageHeight = 0


    init {
        setImageResource(R.drawable.avator)
        val pair = getDrawableResW2H(R.drawable.avator)
        imageWith = pair.first
        imageHeight = pair.second
    }

    private fun getDrawableResW2H(@DrawableRes resId: Int): Pair<Int, Int> {
        val option = BitmapFactory.Options()
        BitmapFactory.decodeResource(resources, resId, option)
        return Pair(option.outWidth, option.outHeight)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gesture.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (imageWith.toFloat() / imageHeight >= width / height.toFloat()) {
            bigScale = height.toFloat() / imageHeight
//            smallScale = width.toFloat() / imageWith
        } else {
            bigScale = width.toFloat() / imageWith
//            smallScale = height.toFloat() / imageHeight
        }

        Log.d(TAG, "onSizeChanged: imageWith = $imageWith:===>imageHeight = $imageHeight")
        Log.d(TAG, "onSizeChanged: bigScale = $bigScale:===>smallScale = $smallScale")
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        if (scale) {
            canvas.translate(
                scaleTransactionX,
                scaleTransactionY
            )
        }
        val scaleReal = smallScale + scaleF * (bigScale - smallScale)
        canvas.scale(scaleReal, scaleReal, width / 2f, height / 2f)
        super.onDraw(canvas)
        canvas.restore()
    }

    override fun onDown(e: MotionEvent): Boolean {
        if (e.actionMasked == MotionEvent.ACTION_DOWN) {
            downPoint.x = e.x
            downPoint.y = e.y
        }
        return true
    }

    override fun onShowPress(e: MotionEvent) {

    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.d(TAG, "onSingleTapUp: ")
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
//        Log.d(TAG, "onScroll: 滑动了")
        if (scale) {
            scaleTransactionX -= distanceX
            //这个偏小了
            val maxX = bigScale * imageWith
//            scaleTransactionX = min(scaleTransactionX, maxX)
//            scaleTransactionX = max(scaleTransactionX, -maxX)
            scaleTransactionY -= distanceY
            val maxY = bigScale * imageHeight
            scaleTransactionY = min(scaleTransactionY, maxY)
            scaleTransactionY = max(scaleTransactionY, -maxY)
            Log.d(
                TAG,
                "onScroll:size====》 width:$width  height:$height imageWidth:${bigScale * imageWith} imageHeight:${bigScale * imageHeight}"
            )
            Log.d(
                TAG,
                "onScroll: scaleTransactionX：$scaleTransactionX  scaleTransactionY：$scaleTransactionY"
            )
            invalidate()
        }
        return false
    }

    override fun onLongPress(e: MotionEvent) {

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {

        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return false
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        Log.d(TAG, "onDoubleTap: 双击了")
        scale = !scale

        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        Log.d(TAG, "onDoubleTapEvent: 双击事件")
        return false
    }

    private fun animateScale() {
        scaleAnimator?.cancel()
        scaleAnimator = ObjectAnimator.ofFloat(this, "scaleF", 0f, 1f)
        scaleAnimator?.duration = 500
        if (scale) {
            scaleAnimator?.start()
        } else {
            scaleAnimator?.reverse()
        }
    }
}