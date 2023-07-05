package com.lq.helloworld.base

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VM : BaseViewModel,VD: ViewDataBinding> : AppCompatActivity() {
    protected abstract val viewModel: VM
    protected abstract val dataBinding:VD
}