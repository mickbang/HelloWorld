package com.lq.helloworld.activity.savestate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lq.helloworld.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "SaveStateActivity"
class SaveStateActivity : AppCompatActivity() {
    val viewModel:SaveStateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_state)

        lifecycleScope.launch {
            viewModel.nameState.collectLatest {
                Log.d(TAG, "onCreate: $it")
            }
        }
    }
}