package com.lq.calendar.v2


import java.text.SimpleDateFormat
import java.util.*


object CalendarUtils {

    fun getDaysFormCalendar(calendar1: Calendar): MutableList<UIDay> {
        val result = mutableListOf<UIDay>()
        val calendar = calendar1.clone() as Calendar
        val maxDay =
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH) //获得当前日期所在月份有多少天（或者说day的最大值)，用于后面的计算


        val calendarClone = calendar.clone() as Calendar //克隆一个Calendar再进行操作，避免造成混乱

        calendarClone[Calendar.DAY_OF_MONTH] = 1 //将日期调到当前月份的第一天

        val startDayOfWeek = calendarClone[Calendar.DAY_OF_WEEK] //获得当前日期所在月份的第一天是星期几

        calendarClone[Calendar.DAY_OF_MONTH] = maxDay //将日期调到当前月份的最后一天

        val endDayOfWeek = calendarClone[Calendar.DAY_OF_WEEK] //获得当前日期所在月份的最后一天是星期几


        /**
         * 计算上一个月在本月日历页出现的那几天.
         * 比如，startDayOfWeek = 3，表示当月第一天是星期二，所以日历向前会空出2天的位置，那么让上月的最后两天显示在星期日和星期一的位置上.
         */
        /**
         * 计算上一个月在本月日历页出现的那几天.
         * 比如，startDayOfWeek = 3，表示当月第一天是星期二，所以日历向前会空出2天的位置，那么让上月的最后两天显示在星期日和星期一的位置上.
         */
        val startEmptyCount = startDayOfWeek - 1 //上月在本月日历页因该出现的天数。

        val preCalendar = calendar.clone() as Calendar //克隆一份再操作

        preCalendar[Calendar.DAY_OF_MONTH] = 1 //将日期调到当月第一天

        preCalendar.add(Calendar.DAY_OF_MONTH, -startEmptyCount) //向前推移startEmptyCount天

        for (i in 0 until startEmptyCount) {
            val dateInfo = UIDay(preCalendar.clone() as Calendar) //使用DateInfo来储存所需的相关信息
            dateInfo.isCurrentMonth = false
            result.add(dateInfo) //将日期添加到数组中
            preCalendar.add(Calendar.DAY_OF_MONTH, 1) //向后推移一天
        }

        /**
         * 计算当月的每一天日期
         */
        /**
         * 计算当月的每一天日期
         */
        calendar[Calendar.DAY_OF_MONTH] = 1 //由于是获取当月日期信息，所以直接操作当月Calendar即可。将日期调为当月第一天

        for (i in 0 until maxDay) {
            val dateInfo = UIDay(calendar.clone() as Calendar)
//            dateInfo.setDate(calendar!!.time)
//            dateInfo.setType(DateInfo.CURRENT_MONTH) //标记日期信息的类型为当月
            if (calendar[Calendar.DAY_OF_MONTH] == calendar1[Calendar.DAY_OF_MONTH]) {
                dateInfo.isSelected = true
            }

            if (i % 5 == 1) {
                dateInfo.jobCount = i
            }

            if (i % 8 == 1) {
//                dateInfo.drawable = BitmapFactory.decodeResource(
//                    App.instance.resources,
//                    R.drawable.frame
//                )
            }

            result.add(dateInfo)
            calendar.add(Calendar.DAY_OF_MONTH, 1) //向后推移一天
        }

        /**
         * 计算下月在本月日历页出现的那几天。
         * 比如，endDayOfWeek = 6，表示当月第二天是星期五，所以日历向后会空出1天的位置，那么让下月的第一天显示在星期六的位置上。
         */
        /**
         * 计算下月在本月日历页出现的那几天。
         * 比如，endDayOfWeek = 6，表示当月第二天是星期五，所以日历向后会空出1天的位置，那么让下月的第一天显示在星期六的位置上。
         */
        val endEmptyCount = 7 - endDayOfWeek //下月在本月日历页上因该出现的天数

        val afterCalendar = calendar!!.clone() as Calendar //同样，克隆一份在操作

        for (i in 0 until endEmptyCount) {
            val dateInfo = UIDay(afterCalendar.clone() as Calendar)
            dateInfo.isCurrentMonth = false
//            dateInfo.setDate(afterCalendar.time)
//            dateInfo.setType(DateInfo.AFTER_MONTH) //将DateInfo类型标记为下个月
            result.add(dateInfo)
            afterCalendar.add(Calendar.DAY_OF_MONTH, 1) //向后推移一天
        }
        return result
    }


    fun getDaysWeekFormCalendar(calendar: Calendar): MutableList<UIDay> {
        val result = mutableListOf<UIDay>()
        val calendarClone = calendar.clone() as Calendar

        calendarClone[Calendar.DAY_OF_WEEK] = 1
        for (i in 0 until 7) {

            val dateInfo = UIDay(calendarClone.clone() as Calendar)
//            dateInfo.setDate(calendar!!.time)
//            dateInfo.setType(DateInfo.CURRENT_MONTH) //标记日期信息的类型为当月
            if (calendar[Calendar.DAY_OF_WEEK] == calendarClone[Calendar.DAY_OF_WEEK]) {
                dateInfo.isSelected = true
            }

            if (i % 5 == 1) {
                dateInfo.jobCount = i
            }

            if (i % 8 == 1) {
//                    dateInfo.drawable = BitmapFactory.decodeResource(
//                        App.instance.resources,
//                        R.drawable.frame
//                    )
            }
            dateInfo.isCurrentMonth =
                calendar.get(Calendar.MONTH) == calendarClone.get(Calendar.MONTH)
            result.add(dateInfo)
            calendarClone.add(Calendar.DAY_OF_WEEK, 1) //向后推移一天
        }
        return result
    }


    fun getPreCalendar(calendar: Calendar, isWeekState: Boolean): Calendar {
        val calendar1 = calendar.clone() as Calendar
        if (isWeekState) {
            calendar1.add(Calendar.DAY_OF_MONTH, -7) //向后推移一天
        } else {
            calendar1.add(Calendar.MONTH, -1) //向后推移一天
        }
        return calendar1
    }

    fun getNextCalendar(calendar: Calendar, isWeekState: Boolean): Calendar {
        val calendar1 = calendar.clone() as Calendar
        if (isWeekState) {
            calendar1.add(Calendar.DAY_OF_MONTH, 7) //向后推移一天
        } else {
            calendar1.add(Calendar.MONTH, 1) //向后推移一天
        }
        return calendar1
    }

    fun getNowCalendar(): Calendar {
        return Calendar.getInstance()
    }

    //只能三项
    fun getFirstCalendar(calendar: Calendar, isWeekState: Boolean): Array<Calendar> {
        val preview = getPreCalendar(calendar, isWeekState)
        val next = getNextCalendar(calendar, isWeekState)
        return arrayOf(
            preview,
            calendar,
            next,
        )
    }

    fun formatMothCalendarTitle(calendar: Calendar): String {
        return SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).format(calendar.time)
    }
}