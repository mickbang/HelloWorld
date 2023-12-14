package com.lq.helloworld.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup

class TouchView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }


//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        var result;
//        if (onInterceptTouchEvent()){
//            result = onTouchEvent()
//        }
//        for (i in 0..childCount) {
//            val dispatcher = child.dispatchTouchEvent(ev)
//            if (dispatcher){
//                return true
//            }
//        }
//        return onTouchEvent()
////        return super.dispatchTouchEvent(ev)
//    }
//
//    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        return super.onInterceptTouchEvent(ev)
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        return super.onTouchEvent(event)
//    }




}