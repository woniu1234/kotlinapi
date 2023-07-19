package com.architecture.apilib.base

import android.util.Log
import com.architecture.apilib.core.HttpResponseCallAdapterFactory
import com.architecture.apilib.core.MoshiResultTypeAdapterFactory
import com.architecture.apilib.interceptor.CommonRequestInterceptor
import com.architecture.apilib.interceptor.CommonResponseInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseApiService(private val baseUrl: String) {

    private var mRetrofit: Retrofit

    open fun <T> getService(service: Class<T>): T {
        return mRetrofit.create(service)
    }

    private val moshi by lazy {
        Moshi.Builder()
            //添加返回的json 数据自定义解析器
            //全局的错误处理器 getHttpWrapperHandler()
            .add(MoshiResultTypeAdapterFactory(getHttpWrapperHandler()))
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    init {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl(baseUrl)
        retrofitBuilder.addConverterFactory(MoshiConverterFactory.create(moshi))
        retrofitBuilder.addCallAdapterFactory(
            HttpResponseCallAdapterFactory(getGlobalErrorHandler()) //全局的错误处理器
        )
        retrofitBuilder.client(getOkHttpClient())
        mRetrofit = retrofitBuilder.build()
    }

    open fun getGlobalErrorHandler(): HttpResponseCallAdapterFactory.ErrorHandler {
        return HttpResponseCallAdapterFactory.ErrorHandler { throwable ->
            Log.e(throwable.message, throwable.code.toString())
        }
    }

    open fun getOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        if (getInterceptorList().isNotEmpty()) {
            getInterceptorList().forEach {
                okHttpClientBuilder.addInterceptor(it)
            }
        }

        //只有在调试状态才需要打印日志
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

        //默认值设置低一点，以便暴露出整个系统的问题所在。
        okHttpClientBuilder
            .retryOnConnectionFailure(true)//默认重试一次，若需要重试N次，则要实现拦截器。
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .callTimeout(2, TimeUnit.MINUTES)
        return okHttpClientBuilder.build()
    }

    open fun getInterceptorList(): List<Interceptor> = arrayListOf<Interceptor>().apply {
        add(CommonRequestInterceptor())
        add(CommonResponseInterceptor())
    }

    open fun getHttpWrapperHandler(): MoshiResultTypeAdapterFactory.HttpWrapper {

        return object : MoshiResultTypeAdapterFactory.HttpWrapper {
            override fun getStatusCodeKey(): String {
                return "code"
            }

            override fun getErrorMsgKey(): String {
                return "status"
            }

            override fun getDataKey(): String {
                return "data"
            }

            override fun isRequestSuccess(statusCode: Int?): Boolean {
                return statusCode == 0
            }

        }
    }

}