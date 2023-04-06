package com.lq.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lq.calendar.v2.OnCalendarSelectorListener
import com.lq.helloworld.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity(), OnCalendarSelectorListener {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.calendarView.onCalendarSelectorListener = this

//        binding.btnSwitch.setOnClickListener{
//            binding.monthView.isWeekState = !binding.monthView.isWeekState
//        }


//        binding.viewPager.adapter = Month2WeekViewPagerAdapter(this)
    }

    override fun onCalendarSelect(date: Calendar) {
        println("选择的日期：${ SimpleDateFormat("yyyy-MM-dd ").format(date.time)}" )
    }
}