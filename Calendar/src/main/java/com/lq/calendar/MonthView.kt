package com.lq.calendar

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import java.util.*

/**
 *
 */
class MonthView : View, View.OnClickListener {
    private var gridWidth: Int = 0
    private var gridHeight = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        45f,
        Resources.getSystem().displayMetrics
    ).toInt()
    private val gridTopPadding = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        6f,
        Resources.getSystem().displayMetrics
    )
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val textColor = Color.parseColor("#FFFFFF")
    private val textSize = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        16f,
        Resources.getSystem().displayMetrics
    )
    private val days = mutableListOf<UIDay>()
    private val textRectArray = mutableListOf<Rect>()
    private val textBounds = Rect()
    var calendar: Calendar? = null
        set(value) {
            field = value
            refreshDueStateChange()
        }
    private val bgCornerPathEffect = CornerPathEffect(dpToPx(6).toFloat())
    private val bottomRoundRadius = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        8f,
        Resources.getSystem().displayMetrics
    )

    private val bottomRoundInnerTextSize = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        11f,
        Resources.getSystem().displayMetrics
    )

    private val bottomRoundColor = Color.parseColor("#379657")
    private val bottomRoundMargin = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        5f,
        Resources.getSystem().displayMetrics
    )

    private val bottomRoundPadding = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        3f,
        Resources.getSystem().displayMetrics
    )

    var isWeekState = true
        set(value) {
            field = value
            refreshDueStateChange()
        }
    var preSelectItem: UIDay? = null
    var clickTempItem: UIDay? = null
    var onCalendarSelectorListener: OnMonthViewSelectorListener? = null


    private fun refreshDueStateChange() {
        days.clear()
        calendar?.let { it ->
            val daysGen = if (isWeekState)
                CalendarUtils.getDaysWeekFormCalendar(it)
            else
                CalendarUtils.getDaysFormCalendar(it)
            days.addAll(
                daysGen
            )

            days.forEach {
//                println("绘制的时候："+it.calendar.get(Calendar.DAY_OF_MONTH))
            }
            preSelectItem = daysGen.firstOrNull {
                it.isSelected
            }
        }
        requestLayout()
    }

    init {
        textPaint.style = Paint.Style.FILL
        textPaint.color = textColor
//        textPaint.typeface  ziti
        textPaint.textSize = textSize
        textPaint.textAlign = Paint.Align.CENTER
        setBackgroundColor(Color.parseColor("#005D2D"))
        calendar = Calendar.getInstance()

        setOnClickListener(this)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize =
            (days.size / 7 + (if (days.size % 7 != 0) 1 else 0)) * gridHeight + paddingTop + paddingBottom
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateRect()
    }

    private fun calculateRect() {
        gridWidth = (width - paddingLeft - paddingRight) / 7
        //计算每个item的rect
        var left = paddingLeft
        var top = paddingTop
        textRectArray.clear()
        days.forEachIndexed { index, _ ->
            if (index % 7 == 0) {
                left = paddingLeft
                if (index != 0) {
                    top += gridHeight
                }
            }
            textRectArray.add(
                Rect(
                    left,
                    top,
                    left + gridWidth,
                    top + gridHeight
                )
            )
            left += gridWidth
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        textRectArray.forEachIndexed { index, rect ->
            rect?.let {
                val item = days[index]
                if (item.calendar != null) {
                    if (item.isSelected) {
                        textPaint.color = Color.parseColor("#99FFFFFF")
                        textPaint.style = Paint.Style.FILL
                        textPaint.pathEffect = bgCornerPathEffect
                        canvas.drawRect(it, textPaint)
                        textPaint.pathEffect = null
                    }

//                textPaint.style = Paint.Style.STROKE
//                canvas.drawRect(it, textPaint)

                    textPaint.style = Paint.Style.FILL
                    textPaint.color = if (item.isCurrentMonth) textColor else Color.parseColor("#33FFFFFF")
                    textPaint.textSize = textSize
                    val dayText =
                        if (item.calendar != null) (item.calendar!!.get(Calendar.DAY_OF_MONTH)).toString() else ""
                    textPaint.getTextBounds(dayText, 0, dayText.length, textBounds)
                    canvas.drawText(
                        if (item.calendar != null) (item.calendar!!.get(Calendar.DAY_OF_MONTH)).toString() else "",
                        (it.right + it.left) / 2f,
                        (it.top + gridTopPadding + textBounds.height()),
                        textPaint
                    )
                    if (item.isCurrentMonth) {
                        if (item.jobCount != 0) {
                            val circleY =
                                it.top + gridTopPadding + textBounds.height() + bottomRoundMargin
                            textPaint.color = bottomRoundColor

                            canvas.drawCircle(
                                (it.right + it.left) / 2f,
                                circleY + bottomRoundRadius,
                                bottomRoundRadius,
                                textPaint
                            )

                            textPaint.textSize = bottomRoundInnerTextSize
                            textPaint.color = Color.WHITE
                            val countText = item.jobCount.toString()
                            textPaint.getTextBounds(countText, 0, countText.length, textBounds)
                            canvas.drawText(
                                countText,
                                (it.right + it.left) / 2f,
                                circleY + bottomRoundRadius + textBounds.height() / 2,
                                textPaint
                            )
                        }

                        if (item.drawable != null) {
//                    canvas.drawBitmap(item.drawable,)

                        }
                    }
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x
        val y = event?.y
        clickTempItem = if (x != null && y != null) {
            findPointItem(x, y)
        } else {
            null
        }
        return super.onTouchEvent(event)
    }

    private fun findPointItem(x: Float, y: Float): UIDay? {
        var uIDay: UIDay? = null
        textRectArray.forEachIndexed { index, rect ->
            if (x > rect.left && x < rect.right) {
                if (y > rect.top && y < rect.bottom) {
                    uIDay = days[index]
                    return@forEachIndexed
                }
            }
        }
        return uIDay
    }


    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    override fun onClick(v: View?) {
        if (clickTempItem != null && clickTempItem!!.calendar != null) {
            preSelectItem?.isSelected = false
            clickTempItem?.isSelected = true
            preSelectItem = clickTempItem
            calendar = preSelectItem!!.calendar
            onCalendarSelectorListener?.onSelectDay(preSelectItem!!)
            invalidate()
        }

    }

    interface OnMonthViewSelectorListener {
        fun onSelectDay(date: UIDay)
    }
}