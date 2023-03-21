package com.lq.core_networklistener

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.TextView
import java.lang.ref.WeakReference
import javax.security.auth.callback.Callback


object NetListenerManager {

    private val ignoreList = mutableListOf<Int>()

    fun ignore(activity: Class<*>) {
        ignoreList.add(activity.hashCode())
    }

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {

            }

            override fun onActivityStarted(p0: Activity) {

            }

            override fun onActivityResumed(p0: Activity) {
                if (!ignoreList.contains(p0::class.hashCode())) {
                    NetListener.observer(p0)
                        .setSnackBarEnabled(false)
                        .setLogsEnabled(false)
                        .setCallBack(ActivityNetListener(p0))
                        .setSensitivity(4)
                        .build()
                }
            }

            override fun onActivityPaused(p0: Activity) {

            }

            override fun onActivityStopped(p0: Activity) {
                p0.findViewById<TextView?>(R.id.net_listener_tips_text)?.visibility = View.GONE
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

            }

            override fun onActivityDestroyed(p0: Activity) {
                p0.findViewById<TextView?>(R.id.net_listener_tips_text)?.clearAnimation()
                if (!ignoreList.contains(p0::class.hashCode())) {
                    NetListener.unregister(p0.applicationContext)
                }
            }

        })
    }
}

class ActivityNetListener(activity: Activity) :
    InternetConnectionListener {
    private val activityWeak by lazy {
        WeakReference<Activity>(activity)
    }

    private val translateAniShow by lazy {
        TranslateAnimation(
            Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
            0f,//fromXValue表示开始的X轴位置
            Animation.RELATIVE_TO_SELF,
            0f,//fromXValue表示结束的X轴位置
            Animation.RELATIVE_TO_SELF,
            1f,//fromXValue表示开始的Y轴位置
            Animation.RELATIVE_TO_SELF,
            0f
        ).apply {
            duration = 300
        }
    }

    private val translateAniHide by lazy {
        TranslateAnimation(
            Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
            0f,//fromXValue表示开始的X轴位置
            Animation.RELATIVE_TO_SELF,
            0f,//fromXValue表示结束的X轴位置
            Animation.RELATIVE_TO_SELF,
            0f,//fromXValue表示开始的Y轴位置
            Animation.RELATIVE_TO_SELF,
            1f
        ).apply {
            duration = 500
        }
    }

    override fun onConnected(source: Int) {
        activityWeak.get()?.let {
            val container = it.findViewById<FrameLayout>(android.R.id.content)
            var tipsText = container.findViewById<TextView?>(R.id.net_listener_tips_text)
            tipsText?.text = "BACK ONLINE"
            tipsText?.setBackgroundColor(Color.parseColor("#005D2D"))
            if (tipsText?.visibility == View.VISIBLE) {
                tipsText?.startAnimation(translateAniHide)
            }
            tipsText?.visibility = View.GONE
        }
    }

    override fun onDisconnected(): View? {
        activityWeak.get()?.let {
            val container = it.findViewById<FrameLayout>(android.R.id.content)
            var tipsText = container.findViewById<TextView?>(R.id.net_listener_tips_text)
            if (tipsText == null) {
                tipsText = TextView(it)
                tipsText.setTextColor(Color.WHITE)
                tipsText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f)
                tipsText.id = R.id.net_listener_tips_text
                tipsText.gravity = Gravity.CENTER_HORIZONTAL
//                tipsText.setPadding(0, dp2px(5f).toInt(), 0, dp2px(5f).toInt())
                val layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.gravity = Gravity.BOTTOM
                container.addView(tipsText, layoutParams)
            }
            tipsText.text = "NO INTERNET CONNECTION"
            tipsText.setBackgroundColor(Color.parseColor("#DE2828"))
            tipsText.clearAnimation()
            tipsText.startAnimation(translateAniShow)
            tipsText.visibility = View.VISIBLE
            return tipsText
        }
        return null
    }


    private fun dp2px(dpValue: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            Resources.getSystem().displayMetrics
        )
    }
}