package com.lq.helloworld.activity.savestate

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SaveStateViewModel(private val saveState:SavedStateHandle):ViewModel() {

    val nameState = saveState.getStateFlow("param","没有取到值")

}