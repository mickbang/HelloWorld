package com.client.rxjava3library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dalvik.system.DexClassLoader
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Single.just("333").map {
            it
        }.subscribe(object :SingleObserver<String>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {

            }

            override fun onSuccess(t: String) {

            }
        })



        val observable = Observable.interval(1_000,TimeUnit.MICROSECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<Long>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

                override fun onNext(t: Long) {

                }
            })


        Single.just("")
            .delay(1_000,TimeUnit.MICROSECONDS,Schedulers.io())
            .subscribe(object :SingleObserver<String>{
                override fun onSubscribe(d: Disposable) {
                    TODO("Not yet implemented")
                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onSuccess(t: String) {
                    TODO("Not yet implemented")
                }
            })



    }
}