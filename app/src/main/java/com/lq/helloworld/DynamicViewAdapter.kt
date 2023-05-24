@file:Suppress("DEPRECATION")

package com.lq.helloworld

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * 可以动态增删的viewAdapter
 */
class DynamicViewAdapter(
    private val fragmentManager: FragmentManager,
    private val fragments: List<Fragment>
) :
    FragmentStatePagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]


    override fun getItemPosition(`object`: Any): Int {
        //如果fragment已经添加并且还在列表里就不刷新
        return if ((`object` as Fragment).isAdded && fragments.contains(`object`)) {
            super.getItemPosition(`object`)
        } else {
            POSITION_NONE
        }
    }

    /**
     * 同位置和list中的Fragment相同
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragmentAdd = super.instantiateItem(container, position) as Fragment
        val fragment = fragments[position]
        return if (fragment == fragmentAdd) {
            fragmentAdd
        } else {
            fragmentManager.beginTransaction().add(container.id, fragment).commitAllowingStateLoss()
            fragment
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (fragments.contains(`object` as Fragment)) {
            super.destroyItem(container, position, `object`)
            return
        } else {
            fragmentManager.beginTransaction().remove(`object`).commitAllowingStateLoss()
        }
    }

}