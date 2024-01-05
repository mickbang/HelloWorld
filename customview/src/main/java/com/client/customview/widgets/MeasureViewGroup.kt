package com.client.customview.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class MeasureViewGroup(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val childCountCus = childCount
        for (i in 0 until childCountCus) {
            val child = getChildAt(i)
            val childWidthSpec = getChildMeasureCus(child.layoutParams.width,widthMeasureSpec)
            val childHeightSpec = getChildMeasureCus(child.layoutParams.height,heightMeasureSpec)
            child.measure(childWidthSpec,childHeightSpec)
        }
    }

    private fun getChildMeasureCus(size:Int, parentSpec: Int):Int {
        val parentSpecMode = MeasureSpec.getMode(parentSpec)
        val parentSpecSize = MeasureSpec.getSize(parentSpec)
        val afterMode:Int
        val afterSize:Int
        when (size) {
            LayoutParams.MATCH_PARENT -> {
                when (parentSpecMode) {
                    MeasureSpec.AT_MOST -> {
                        afterSize = size
                        afterMode = MeasureSpec.AT_MOST
                    }
                    MeasureSpec.EXACTLY -> {
                        afterSize = parentSpecSize
                        afterMode = MeasureSpec.EXACTLY
                    }
                    MeasureSpec.UNSPECIFIED -> {
                        afterSize = size
                        afterMode = MeasureSpec.UNSPECIFIED
                    }
                    else -> {
                        afterSize = size
                        afterMode = MeasureSpec.UNSPECIFIED
                    }
                }
            }

            LayoutParams.WRAP_CONTENT -> {
                when (parentSpecMode) {
                    MeasureSpec.AT_MOST -> {
                        afterSize = size
                        afterMode = MeasureSpec.AT_MOST
                    }
                    MeasureSpec.EXACTLY -> {
                        afterSize = size
                        afterMode = MeasureSpec.AT_MOST
                    }
                    MeasureSpec.UNSPECIFIED -> {
                        afterSize = size
                        afterMode = MeasureSpec.UNSPECIFIED
                    }
                    else -> {
                        afterSize = size
                        afterMode = MeasureSpec.UNSPECIFIED
                    }
                }
            }

            else -> {
                afterSize = size
                afterMode = MeasureSpec.EXACTLY
            }
        }
        return MeasureSpec.makeMeasureSpec(afterSize,afterMode)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    override fun getLayoutParams(): LayoutParams {
        return super.getLayoutParams()
    }
}