package com.client.customview.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

class EventView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
//        return onTouchEvent()

        return super.dispatchTouchEvent(event)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}


class EventViewGroup(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs){
    //事件分发
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        if (disallowIntercept||!onInterceptTouchEvent()) {
//           // 循环调用子类的dispatchTouchEvent
//        if (child.dispatchTouchEvent){
//            return true
//        }
//        }
//        return onTouchEvent()

        return super.dispatchTouchEvent(ev)
    }
    //事件拦截
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }
    //事件消费
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }
}