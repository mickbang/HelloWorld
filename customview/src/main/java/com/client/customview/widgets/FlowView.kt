package com.client.customview.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import com.client.common.dp

class FlowView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val colors = arrayOf(Color.BLUE, Color.BLACK, Color.RED, Color.GREEN,Color.YELLOW,Color.CYAN)
    private val texts = arrayOf("张三张三张三", "李四李四李四李四李四李四", "王二王二", "赵三san")

    init {
        setTextColor(Color.WHITE)
        text = randomText()
        setPadding(5.dp, 5.dp*random(), 5.dp, 5.dp)
        gravity = Gravity.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(randomColor())
        super.onDraw(canvas)
    }

    private fun randomText(): String =
        texts[((Math.random() * 10f).toInt()) % 3]


    private fun randomColor(): Int =
        colors[((Math.random() * 10f).toInt()) % 5]


    private fun random():Int =  ((Math.random() * 10f).toInt()) % 5
}