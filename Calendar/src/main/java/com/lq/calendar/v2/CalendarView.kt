package com.lq.calendar.v2

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.lq.calendar.R
import com.lq.calendar.databinding.ViewCalendarBinding
import java.util.Calendar

class CalendarView : LinearLayoutCompat, MonthView.OnMonthViewSelectorListener {
    private val binding: ViewCalendarBinding
    private var isWeekState = true
        set(value) {
            field = value
            binding.btnSwitch.setImageResource(if (value) R.drawable.ic_calendar_close else R.drawable.ic_calendar_open)
            currentSelectCalendar?.let {
                calendarData[1] = it
                calendarData[0] = CalendarUtils.getPreCalendar(it, isWeekState)
                calendarData[2] = CalendarUtils.getNextCalendar(it, isWeekState)
            }
            adapter.notifyDataSetChanged()
        }

    val calendarData = CalendarUtils.getFirstCalendar(CalendarUtils.getNowCalendar(), isWeekState)

    var onCalendarSelectorListener: OnCalendarSelectorListener? = null
        set(value) {
            field = value
            this@CalendarView.onCalendarSelectorListener?.onCalendarSelect(calendarData[1])
        }
    var currentSelectCalendar = CalendarUtils.getNowCalendar()

    private val adapter by lazy {
        CalendarAdapter()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setBackgroundColor(Color.parseColor("#005D2D"))
        orientation = VERTICAL
        binding = ViewCalendarBinding.inflate(LayoutInflater.from(context), this)
        binding.btnSwitch.setOnClickListener {
            isWeekState = !isWeekState
        }

        binding.vp2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.vp2.adapter = adapter

        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 1) {
                    setTitle(calendarData[1])
                    this@CalendarView.onCalendarSelectorListener?.onCalendarSelect(calendarData[1])
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    binding.vp2.post {
                        resetPosition(binding.vp2.currentItem)
                    }
                }
            }
        })

        binding.preBtn.setOnClickListener {
            binding.vp2.currentItem = 0
        }

        binding.nextBtn.setOnClickListener {
            binding.vp2.currentItem = 2
        }

        initCalendar()
    }

    private fun resetPosition(position: Int) {
        if (position != 1) {
            val current = calendarData[position]
            calendarData[1] = current
            calendarData[0] = CalendarUtils.getPreCalendar(current, isWeekState)
            calendarData[2] = CalendarUtils.getNextCalendar(current, isWeekState)
            binding.vp2.setCurrentItem(1, false)
            adapter.notifyDataSetChanged()
        }
    }

    private fun initCalendar() {
        binding.vp2.setCurrentItem(1, false)
        setTitle(calendarData[1])
    }

    private fun setTitle(calendar: Calendar) {
        binding.tvMonth.text = CalendarUtils.formatMothCalendarTitle(calendar)
    }

    inner class CalendarAdapter : RecyclerView.Adapter<CalendarViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
            val monthView = MonthView(context)
            monthView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            return CalendarViewHolder(monthView)
        }

        override fun getItemCount(): Int {
            return calendarData.size
        }

        override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
            holder.month.calendar = calendarData[position]
            holder.month.isWeekState = isWeekState
            if (position == binding.vp2.currentItem) {
                holder.month.onCalendarSelectorListener = this@CalendarView
            } else {
                holder.month.onCalendarSelectorListener = null
            }
        }
    }

    inner class CalendarViewHolder(val month: MonthView) : RecyclerView.ViewHolder(month)

    override fun onSelectDay(date: UIDay) {
        if (date.calendar == null || currentSelectCalendar == date.calendar) {
            return
        }
        currentSelectCalendar = date.calendar
        binding.vp2.post {
            if (currentSelectCalendar != null) {
                calendarData[0] =
                    CalendarUtils.getPreCalendar(currentSelectCalendar!!, isWeekState)
                calendarData[1] = currentSelectCalendar!!
                calendarData[2] =
                    CalendarUtils.getNextCalendar(currentSelectCalendar!!, isWeekState)
            }
            adapter.notifyDataSetChanged()
            setTitle(calendarData[1])
        }
        this.onCalendarSelectorListener?.onCalendarSelect(date.calendar)
    }
}