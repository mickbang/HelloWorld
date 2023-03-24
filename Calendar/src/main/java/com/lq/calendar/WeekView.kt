package com.lq.calendar

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue

/**
 *
 */
class WeekView : androidx.appcompat.widget.AppCompatTextView {
    private var gridWidth: Int = 0
    private var gridHeight = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        30f,
        Resources.getSystem().displayMetrics
    ).toInt()
    private val weekTitles = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val textColor = Color.parseColor("#99FFFFFF")
    private val textSize = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        13f,
        Resources.getSystem().displayMetrics
    )
    private val textRectArray = arrayOfNulls<Rect>(weekTitles.size)
    private val textBounds = Rect()

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

    init {
        textPaint.style = Paint.Style.FILL
        textPaint.color = textColor
//        textPaint.typeface  ziti
        textPaint.textSize = textSize
        textPaint.textAlign = Paint.Align.CENTER
        setBackgroundColor(Color.parseColor("#005D2D"))
        textPaint.getTextBounds(weekTitles[0], 0, weekTitles[0].length, textBounds)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = gridHeight + paddingTop + paddingBottom
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        gridWidth = (width - paddingLeft - paddingRight) / 7

        //计算每个item的rect
        var left = paddingLeft
        weekTitles.forEachIndexed { index, _ ->
            textRectArray[index] = Rect(
                left,
                paddingTop,
                left + gridWidth,
                height - paddingBottom
            )
            left += gridWidth
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textRectArray.forEachIndexed { index, rect ->
            rect?.let {
                canvas.drawText(
                    weekTitles[index],
                    (it.right + it.left) / 2f,
                    (it.top + it.bottom) / 2f + (textBounds.height()) / 2f,
                    textPaint
                )
            }
        }
    }


    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density) as Int
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density) as Int
    }
}