package com.client.customview.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class FlowLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var maxLineWidth = 0
        var maxLineHeight = 0
        var childWidthUsed = 0
        var childHeightUsed = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == GONE) {
                continue
            }
            //先以不限制测量子view的宽度
            measureChildWithMargins(
                child,
                MeasureSpec.makeMeasureSpec(
                    MeasureSpec.getSize(widthMeasureSpec),
                    MeasureSpec.UNSPECIFIED
                ),
                0,
                MeasureSpec.makeMeasureSpec(
                    MeasureSpec.getSize(widthMeasureSpec),
                    MeasureSpec.UNSPECIFIED
                ),
                0
            )
            //这里写得比较粗糙，没有处理margin
            if (childWidthUsed + child.measuredWidth > widthSize) {
                childWidthUsed = 0
                childHeightUsed += maxLineHeight
                measureChildWithMargins(
                    child,
                    widthMeasureSpec,
                    childWidthUsed,
                    heightMeasureSpec,
                    childHeightUsed
                )
                childWidthUsed += child.measuredWidth
                maxLineWidth = maxLineWidth.coerceAtLeast(childWidthUsed)
                maxLineHeight = child.measuredHeight
            } else {
                measureChildWithMargins(
                    child,
                    widthMeasureSpec,
                    childWidthUsed,
                    heightMeasureSpec,
                    childHeightUsed
                )
                childWidthUsed += child.measuredWidth
                maxLineWidth = maxLineWidth.coerceAtLeast(childWidthUsed)
                maxLineHeight = maxLineHeight.coerceAtLeast(child.measuredHeight)
            }
        }
        //加上最后一行的高度
        childHeightUsed += maxLineHeight
        setMeasuredDimension(
            resolveSize(maxLineWidth, widthMeasureSpec),
            resolveSize(childHeightUsed, heightMeasureSpec)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childTop = 0
        var maxLineHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == GONE) {
                continue
            }
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (childLeft + childWidth > r) {
                childLeft = 0
                childTop += maxLineHeight
                maxLineHeight = 0
            }
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
            childLeft += childWidth
            maxLineHeight = maxLineHeight.coerceAtLeast(childHeight)
        }
    }

//    override fun generateDefaultLayoutParams(): MarginLayoutParams {
//        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
//    }

    override fun generateLayoutParams(attrs: AttributeSet?): MarginLayoutParams {
        return MarginLayoutParams(context,attrs)
    }

//    override fun generateLayoutParams(p: LayoutParams?): MarginLayoutParams {
//        return MarginLayoutParams(p)
//    }
}