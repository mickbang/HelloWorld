package com.client.customview.widgets

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import com.blankj.utilcode.util.ImageUtils
import com.client.common.dp
import com.client.customview.R

class CanvasView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val image = ImageUtils.getBitmap(R.drawable.avator, 80.dp, 80.dp)
    private val camera = Camera().apply {
        setLocation(0f, 0f, -6 * resources.displayMetrics.density)
    }
    private val margin = 100.dp.toFloat()
    private var ZRotate = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var XRotate = 0f
        set(value) {
            field = value
            invalidate()
        }

    private var ZRotateTop = 0f
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画上半部分
        canvas.save()
        canvas.translate(margin + image.width / 2f, margin + image.height / 2f)
        canvas.rotate(-XRotate,0f,0f)
        camera.save()
        camera.rotateX(ZRotateTop)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-image.width.toFloat(), -image.height.toFloat(), image.width.toFloat(), 0f)
        canvas.rotate(XRotate,0f,0f)
        canvas.translate(-margin - image.width / 2f, -margin - image.height / 2f)
        canvas.drawBitmap(image, margin, margin, paint)
        canvas.restore()

        //画下半部分
        canvas.save()
        canvas.translate(margin + image.width / 2f, margin + image.height / 2f)
        canvas.rotate(-XRotate,0f,0f)
        camera.save()
        camera.rotateX(ZRotate)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-image.width.toFloat(), 0f, image.width.toFloat(), image.height.toFloat())
        canvas.rotate(XRotate,0f,0f)
        canvas.translate(-margin - image.width / 2f, -margin - image.height / 2f)
        canvas.drawBitmap(image, margin, margin, paint)
        canvas.restore()
    }

    fun startAnimatorCus() {
        ZRotate = 0f
        XRotate = 0f
        ZRotateTop = 0f
        val zAni = ObjectAnimator.ofFloat(this, "ZRotate", 0f, 45f)
        val ani = ObjectAnimator.ofFloat(this, "XRotate", 0f, 360f)
        val ani1 = ObjectAnimator.ofFloat(this, "ZRotateTop", 0f, -45f)
        val set = AnimatorSet()
        set.interpolator = AccelerateDecelerateInterpolator()
        set.setDuration(2 * 1000L)
        set.playSequentially(zAni,ani,ani1)
        set.start()
    }
}