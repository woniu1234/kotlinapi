package com.example.kotlinapidemo.api

import com.architecture.apilib.core.HttpResult
import com.example.kotlinapidemo.beans.TestDataBean
import retrofit2.http.GET

interface IMainApi {
    @GET("companies?_quantity=10&_locale=zh_CN")
    suspend fun testApiData(): HttpResult<List<TestDataBean>>
}