package com.architecture.apilib.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

/**
 * 请求返回拦截器
 */
class CommonResponseInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestTime = System.currentTimeMillis()

        val response = chain.proceed(chain.request())

        // 复制响应体内容
        val rawJson = response.body.string()

        // 记录请求时长
        Log.d(TAG, "requestTime=" + (System.currentTimeMillis() - requestTime))
        Log.d(TAG, "responseBody=$rawJson") // 可选：记录响应内容

        // 重新构建 Response，因为原 body 已被消耗
        val newResponse = response.newBuilder()
            .body(rawJson.toResponseBody(response.body.contentType()))
            .build()

        return newResponse
    }

    companion object {
        private const val TAG = "ResponseInterceptor"
    }
}
