package com.client.customview.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.ListFormatter.Width
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import kotlin.math.max
import kotlin.math.min

class ViewPagerCus(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private val gestureDetectorListener = ViewPagerCusGestureListener()
    private val gestureDetector = GestureDetectorCompat(context, gestureDetectorListener)

    private var childLeftOffset = 0
    private val halfRunnable = HalfRunnable()

    private val overScroll = OverScroller(context)
    private val flingRunnable = FlingRunnable()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = childLeftOffset
        var childTop = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.layout(childLeft, childTop, childLeft + width, childTop + height)
            childLeft += child.measuredWidth
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_UP) {
            ViewCompat.postOnAnimation(this@ViewPagerCus, halfRunnable)
        }
        return gestureDetector.onTouchEvent(event)
    }


    inner class ViewPagerCusGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            childLeftOffset -= distanceX.toInt()
            childLeftOffset = min(childLeftOffset, 0)
            childLeftOffset = max(childLeftOffset, -width)
            requestLayout()
            return false
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            overScroll.fling(childLeftOffset, 0, velocityX.toInt(), 0, -width, 0, 0, 0)
            ViewCompat.postOnAnimation(this@ViewPagerCus, flingRunnable)
            return false
        }
    }

    inner class HalfRunnable : Runnable {
        override fun run() {
            if (!overScroll.isFinished) {
                ViewCompat.postOnAnimation(this@ViewPagerCus, this@HalfRunnable)
                return
            }
            if (childLeftOffset < -width / 2f) {
                childLeftOffset -= 20
                if (childLeftOffset > -width) {
                    ViewCompat.postOnAnimation(this@ViewPagerCus, this@HalfRunnable)
                }
                childLeftOffset = max(childLeftOffset, -width)
                requestLayout()
            } else {
                childLeftOffset += 20
                if (childLeftOffset < 0) {
                    ViewCompat.postOnAnimation(this@ViewPagerCus, this@HalfRunnable)
                }
                childLeftOffset = min(childLeftOffset, 0)
                requestLayout()
            }
        }
    }

    inner class FlingRunnable : Runnable {
        override fun run() {
            if (overScroll.finalX == 0 || overScroll.finalX == -width) {
                if (overScroll.computeScrollOffset()) {
                    childLeftOffset = overScroll.currX
                    requestLayout()
                    ViewCompat.postOnAnimation(this@ViewPagerCus, this@FlingRunnable)
                }
            } else {
                overScroll.forceFinished(true)
            }
        }
    }

}