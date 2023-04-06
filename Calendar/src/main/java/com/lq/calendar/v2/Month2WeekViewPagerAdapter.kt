package com.lq.calendar.v2

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class Month2WeekViewPagerAdapter(val context: Context, viewPager: ViewPager) : PagerAdapter(),
    ViewPager.OnPageChangeListener {
    val views = mutableListOf<MonthView>()

    init {
        views.add(MonthView(context))

        viewPager.adapter = this
        viewPager.addOnPageChangeListener(this)
    }

    override fun getCount(): Int {
        return views.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(views[position])
        return views.get(position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(views[position])
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

}