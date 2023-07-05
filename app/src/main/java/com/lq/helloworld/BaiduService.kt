package com.lq.helloworld

import retrofit2.http.GET

interface BaiduService {
    @GET("https://www.baidu.com")
    suspend fun getBaiDu():String
}