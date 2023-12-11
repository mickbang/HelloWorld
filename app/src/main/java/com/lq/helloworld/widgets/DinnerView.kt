package com.lq.helloworld.widgets

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.SizeUtils

private val dinners = listOf(
    "面条",
    "炒菜",
    "饺子",
    "混沌",
    "方便面",
    "水煮鱼",
    "臊子面",
    "清蒸鱼",
    "水煮鱼",
    "水煮肉片",
)

class DinnerView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = SizeUtils.dp2px(20f).toFloat()
    }

    var dinner = "面条"
        set(value) {
            field = value
            invalidate()
        }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawText(dinner, width / 2f, height / 2f, paint)
    }

}

class DinnerEvaluator : TypeEvaluator<String> {
    override fun evaluate(fraction: Float, startValue: String?, endValue: String?): String {
        val startIndex = dinners.indexOf(startValue)
        val endIndex = dinners.indexOf(endValue)
        val current = (startIndex+fraction*(endIndex-startIndex)).toInt()
        return dinners[current]
    }
}