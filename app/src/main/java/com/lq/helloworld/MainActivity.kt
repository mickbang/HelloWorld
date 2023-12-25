package com.lq.helloworld

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.lq.helloworld.databinding.ActivityMainBinding
import dalvik.system.DexClassLoader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(/* activity = */ this,/* layoutId = */
                R.layout.activity_main
            )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.apply {
            btnUpdate.setOnClickListener {
                this@MainActivity.viewModel.onIntent(MainUIIntent.Update)
            }

            btnClear.setOnClickListener {
                this@MainActivity.viewModel.onIntent(MainUIIntent.Delete)
            }
        }


        lifecycleScope.launch {
            viewModel.result.collectLatest {
                when (it) {
                    MainUIState.Error -> showError()
                    MainUIState.Loading -> showLoading()
                    is MainUIState.Success -> showContent(it.text)
                }
            }
        }

        viewModel.getData()
    }

    private fun showContent(text: String) {
        Log.d(TAG, "showContent: $text")
    }

    private fun showLoading() {

    }

    private fun showError() {

    }


}