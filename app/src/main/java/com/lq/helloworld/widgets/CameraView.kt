package com.lq.helloworld.widgets

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave
import com.blankj.utilcode.util.ImageUtils
import com.lq.helloworld.R

class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val bitmap = ImageUtils.getBitmap(R.mipmap.ic_launcher)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clipped = Path().apply {
        addOval(100f, 300f, 100f + bitmap.width, 300f + bitmap.height, Path.Direction.CCW)
    }

    private val camera = Camera()

    var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bottomFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var roationFlip = 0f
        set(value) {
            field = value
            invalidate()
        }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.withSave {
            canvas?.translate((100f + bitmap.width / 2f), (300f + bitmap.height / 2f))
            canvas?.rotate(-roationFlip)
            camera.save()
            camera.rotateX(topFlip)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas?.clipRect(
                -bitmap.width.toFloat(),
                -bitmap.height.toFloat(),
                bitmap.width.toFloat(),
                0f
            )
            canvas?.rotate(roationFlip)
            canvas?.translate(-(100f + bitmap.width / 2f), -(300f + bitmap.height / 2f))
            canvas?.drawBitmap(bitmap, 100f, 300f, paint)
        }
        canvas?.withSave {
            canvas?.translate((100f + bitmap.width / 2f), (300f + bitmap.height / 2f))
            canvas?.rotate(-roationFlip)
            camera.save()
            camera.rotateX(bottomFlip)
            camera.applyToCanvas(canvas)
            camera.restore()
            canvas?.clipRect(
                -bitmap.width.toFloat(),
                0f,
                bitmap.width.toFloat(),
                bitmap.height.toFloat()
            )
            canvas?.rotate(roationFlip)
            canvas?.translate(-(100f + bitmap.width / 2f), -(300f + bitmap.height / 2f))
            canvas?.drawBitmap(bitmap, 100f, 300f, paint)
        }
    }


    fun startAni(){
        val bottomAnimator = ObjectAnimator.ofFloat(this, "bottomFlip", 60f)
        bottomAnimator.startDelay = 1000
        bottomAnimator.duration = 1000
        val roatioAnimator = ObjectAnimator.ofFloat(this, "roationFlip", 360f)
        roatioAnimator.startDelay = 1000
        roatioAnimator.duration = 1000
        val topAnimator = ObjectAnimator.ofFloat(this, "topFlip", -60f)
        topAnimator.startDelay = 1000
        topAnimator.duration = 1000
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(bottomAnimator, roatioAnimator, topAnimator)
        animatorSet.start()
    }

    fun startAni2(){
        val bottomAnimator = PropertyValuesHolder.ofFloat("bottomFlip",60f)
        val roatioAnimator = PropertyValuesHolder.ofFloat("roationFlip",360f)
        val topAnimator = PropertyValuesHolder.ofFloat("topFlip",-60f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(this,bottomAnimator,roatioAnimator,topAnimator)
        animator.start()
    }
}