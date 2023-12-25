package com.lq.helloworld

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    val etContent = MutableStateFlow("")
    val textShow = MutableStateFlow("I am  MainViewModel")

    val result: StateFlow<MainUIState> = setText().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_1000),
        MainUIState.Loading
    )

    private val _pageData = MutableSharedFlow<String>()
    val pageData = _pageData.asSharedFlow()

    fun onIntent(intent: MainUIIntent) {
        when (intent) {
            MainUIIntent.Update -> {
                updateTextShow()
            }

            MainUIIntent.Delete -> {
                clearTextShow()
            }
        }
    }

    private fun clearTextShow() {
        textShow.value = ""
    }

    private fun updateTextShow() {
        textShow.value = etContent.value
    }

    private fun setText(): Flow<MainUIState> {
        return flow<MainUIState> {
            emit(MainUIState.Success("333"))
        }.onStart { emit(MainUIState.Loading) }
            .catch { emit(MainUIState.Error) }
            .flowOn(Dispatchers.IO)
    }


    fun getData() {
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.printStackTrace()
        }) {
            flow<MainUIState> {
                delay(5_000)
                emit(MainUIState.Success("XXXXXXXX"))
            }.onStart {
                emit(MainUIState.Loading)
                Log.d(TAG, "getData: 1")
            }.onCompletion {

                Log.d(TAG, "getData: 3")
            }
                .catch {
                    emit(MainUIState.Error)
                    Log.d(TAG, "getData: 2")
                }
                .shareIn(viewModelScope,SharingStarted.WhileSubscribed(10_000))
                .collectLatest {
                    when (it) {
                        MainUIState.Error -> Log.d(TAG, "getData: 4")
                        MainUIState.Loading -> {
                            Log.d(TAG, "getData: 5")
                            throw NullPointerException("xxx")
                        }

                        is MainUIState.Success -> Log.d(TAG, "getData: 6")
                    }

                }
        }
    }

}

sealed interface MainUIIntent {
    object Update : MainUIIntent
    object Delete : MainUIIntent
}

sealed interface MainUIState {
    object Loading : MainUIState
    object Error : MainUIState

    data class Success(val text: String) : MainUIState
}