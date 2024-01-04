package com.client.common

import android.content.res.Resources
import com.blankj.utilcode.util.SizeUtils

val Int.dp: Int
    get() = SizeUtils.dp2px(this.toFloat())
val Int.sp:Int
    get() = SizeUtils.sp2px(this.toFloat())