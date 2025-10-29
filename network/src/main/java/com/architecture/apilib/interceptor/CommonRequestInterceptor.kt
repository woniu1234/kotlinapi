package com.architecture.apilib.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 通用的请求拦截器，
 */
class CommonRequestInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Connection", "Keep-Alive")
        return chain.proceed(builder.build())
    }
}
