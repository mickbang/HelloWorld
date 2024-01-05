package com.client.customview.widgets

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import com.client.common.dp
import com.client.common.sp

val foods = arrayOf(
    "甘蔗",
    "苹果",
    "香蕉",
    "橙子",
    "西瓜",
    "葡萄",
    "桃子",
    "李子",
    "草莓",
    "芒果",
    "猕猴桃"
)

class AnimatorView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        textSize = 20.sp.toFloat()
    }
    var text: String = "甘蔗"
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(text, 0f, 20.sp.toFloat(), paint)
    }


    class TextEvaluator : TypeEvaluator<String> {
        override fun evaluate(fraction: Float, startValue: String?, endValue: String?): String {
            val startIndex = foods.indexOf(startValue)
            val endIndex = foods.indexOf(endValue)
            return foods[(startIndex + (endIndex - startIndex) * fraction).toInt()]
        }
    }

    //方式1
    fun startAni1(){
        alpha = 0f
        scaleX = 0f
        scaleY = 0f
        animate()
            .translationX(100.dp.toFloat())
            .translationY(200.dp.toFloat())
            .rotation(360f)
            .alpha(1f)
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setInterpolator(LinearInterpolator())
            .setDuration(1*1000L)
            .start()
    }

    fun startAni2(){
        val translationXAn = ObjectAnimator.ofFloat(this, "translationX", 100.dp.toFloat())
        val translationYAn = ObjectAnimator.ofFloat(this, "translationY", 200.dp.toFloat())

        val animatorset = AnimatorSet()
        animatorset.interpolator = LinearInterpolator()
        animatorset.duration = 1 * 1000L
        animatorset.playSequentially(translationXAn, translationYAn)
        animatorset.start()
    }

    fun startAni3(){
        val propertyValuesHolderXAn =
            PropertyValuesHolder.ofFloat("translationX", 0f, 100.dp.toFloat())
        val propertyValuesHolderYAn =
            PropertyValuesHolder.ofFloat("translationY", 0f, 200.dp.toFloat())

        val animator =
            ObjectAnimator.ofPropertyValuesHolder(this,propertyValuesHolderXAn, propertyValuesHolderYAn)
        animator.duration = 1 * 1000L
        animator.start()
    }


    fun startCus(){
        ObjectAnimator.ofObject(this,"text", AnimatorView.TextEvaluator(),"甘蔗","猕猴桃").apply {
            duration = 5*1000L
//            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = AccelerateInterpolator()
            start()
        }
    }
}