package com.example.kotlinapidemo.api

import com.architecture.apilib.base.BaseApiService
import com.architecture.apilib.core.HttpResponseCallAdapterFactory
import com.architecture.apilib.core.MoshiResultTypeAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient

open class CommonApi : BaseApiService("https://fakerapi.it/api/v1/") {

    override fun getInterceptorList(): List<Interceptor> {
        return super.getInterceptorList()
    }

    override fun getGlobalErrorHandler(): HttpResponseCallAdapterFactory.ErrorHandler {
        return super.getGlobalErrorHandler()
    }


    override fun getHttpWrapperHandler(): MoshiResultTypeAdapterFactory.HttpWrapper {
        return super.getHttpWrapperHandler()
    }

    override fun getOkHttpClient(): OkHttpClient {
        return super.getOkHttpClient()
    }
}