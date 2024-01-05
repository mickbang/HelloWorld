package com.client.customview.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ImageUtils
import com.client.common.dp
import com.client.customview.R

/**
 *
 */

class CircleView : View {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val image = ImageUtils.getBitmap(R.drawable.avator, 50.dp, 50.dp)
    private val circlePath = Path()
    private val VER_MARGIN = 30.dp
    private var srcRectF = RectF()
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
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

    private fun init(attrs: AttributeSet?, defStyle: Int) {

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        srcRectF = RectF(0f,0f,width.toFloat(),height.toFloat())
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var picTop = 0f
        val picLeft = width / 2f - image.width / 2f
        canvas.drawBitmap(image, picLeft, picTop, paint)

        //使用clipPath的方法显示圆形头像
        picTop = VER_MARGIN.toFloat() + image.height
        canvas.save()
        circlePath.reset()
        circlePath.addCircle(
            width / 2f,
            picTop + image.height / 2f,
            image.height / 2f,
            Path.Direction.CW
        )
        canvas.drawPath(circlePath, paint)
        canvas.clipPath(circlePath)
        canvas.drawBitmap(
            image,
            picLeft,
            picTop,
            paint
        )
        canvas.restore()

        //使用xf
        picTop = VER_MARGIN.toFloat() + image.height * 2 + VER_MARGIN.toFloat()
        val save = canvas.saveLayer(srcRectF,null)
        canvas.drawCircle(picLeft+image.width/2f,picTop+image.height/2f,image.height/2f,paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(image, picLeft, picTop, paint)
        paint.setXfermode(null)
        canvas.restoreToCount(save)

    }
}