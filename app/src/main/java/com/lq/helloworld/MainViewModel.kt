package com.lq.helloworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val networkApi = Retrofit.Builder()
        .baseUrl("https://www.baidu.com")
        .client(OkHttpClient.Builder().build())
        .build()
        .create(BaiduService::class.java)

    val content= flow {
        emit("ssss")
    }.asLiveData()

//    private val data: Flow<String> = flow {
//        val respose = networkApi.getBaiDu()
//        emit(respose)
//    }.flowOn(Dispatchers.IO)
//        .onCompletion {
//
//        }.launchIn(viewModelScope)

    init {
        flow {
            val respose = networkApi.getBaiDu()
            var ss = get()
            emit(respose)
        }.flowOn(Dispatchers.IO)
            .onCompletion {

            }.launchIn(viewModelScope)

    }


    fun ss(){
//        flow<String> {
//            emit()
//        }

        viewModelScope.launch {

        }

    }


    suspend fun get():String{
    return "11111"
    }
}