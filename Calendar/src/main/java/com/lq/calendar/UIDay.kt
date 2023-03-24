package com.lq.calendar

import android.graphics.Bitmap
import java.util.*

data class UIDay(
    val calendar: Calendar,
    var isSelected: Boolean = false,
    var drawable: Bitmap? = null,
    var jobCount: Int = 0,
    var isCurrentMonth: Boolean = true
) : Cloneable {
    public override fun clone(): Any {
        return UIDay(calendar, isSelected, drawable, jobCount)
    }
}