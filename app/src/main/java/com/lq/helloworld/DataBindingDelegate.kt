package com.lq.helloworld

import android.app.Activity
import android.database.DatabaseUtils
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class DataBindingDelegate<VD : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    ReadOnlyProperty<Activity, VD> {

    private var binding: VD? = null

    override fun getValue(thisRef: Activity, property: KProperty<*>): VD =
        binding ?: DataBindingUtil.setContentView<VD>(thisRef, layoutRes).also { binding = it }

}