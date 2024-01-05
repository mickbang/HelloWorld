package com.client.customview.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.client.common.dp

/**
 *
 */
class MeasureView : View {

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = 50.dp  //模拟宽度，这里写死，也可以根据需求动态计算
        val heightSize = 100.dp//模拟高度，这里写死，也可以根据需求动态计算

//        resolve() 方法可以不自己写，直接只用View.resolveSize()
        setMeasuredDimension(
            resolve(widthMeasureSpec,widthSize),
            resolve(heightMeasureSpec,heightSize)
        )
    }

    private fun resolve(measureSpec: Int, size: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        val result = when (specMode) {
            MeasureSpec.AT_MOST -> {
                if (size < specSize) size else specSize
            }
            MeasureSpec.EXACTLY -> {
                size
            }
            MeasureSpec.UNSPECIFIED -> {
                size
            }
            else -> {
                size
            }
        }
        return result
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.drawColor(Color.BLUE)
    }
}