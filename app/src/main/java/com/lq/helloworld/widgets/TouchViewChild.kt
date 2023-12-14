package com.lq.helloworld.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TouchViewChild(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(event)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}