package com.lq.helloworld.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout

class FlowLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        var left = 0
//        var top = 0
//        var maxWidth = 0
//        var maxHeight = 0
//        for (i in 0 until childCount) {
//            val child = getChildAt(i)
//            val childWidth = child.measuredWidth
//            val childHeight = child.measuredHeight
//            maxWidth = max(maxWidth, childWidth)
//            maxHeight = max(maxHeight, childHeight)
//            if (left + childWidth > r) {
//                left = 0
//                top += maxHeight
//                maxHeight = childHeight
    }
}