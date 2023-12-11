package com.lq.helloworld

import android.animation.AnimatorSet
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.SizeUtils
import com.lq.helloworld.widgets.CameraView
import com.lq.helloworld.widgets.DinnerEvaluator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<View>(R.id.view)
//        view.animate().translationX(SizeUtils.dp2px(150f).toFloat()).setStartDelay(2000L).start()
//        view.startAni2()

//        val keyFrame1 = Keyframe.ofFloat(50f)

//        val animator = ObjectAnimator.ofFloat(view,"translationX",100f)

        val animator = ObjectAnimator.ofObject(view,"dinner",DinnerEvaluator(),"水煮肉片")
        animator.startDelay = 1000L
        animator.duration  = 5*1000L
        animator.start()

//        LinearLayout
    }
}