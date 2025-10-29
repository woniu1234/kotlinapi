package com.architecture.apilib.core

import com.architecture.apilib.error.BusinessException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


/**
 * 重新定义Call,适配到HttpResult 以及错误转换Handler
 */
internal class HttpResponseCall<S : Any>(
    private val call: Call<S>,
    private val errorConverter: HttpResponseCallAdapterFactory.ErrorHandler
) : Call<HttpResult<S>> {

    /**
     * 异步发送请求并通知调用回调返回的 response body或者 错误信息
     *
     *  callback.onResponse(Call<T> call, Response<T> response);
     *  callback.onResponse(this@HttpResponseCall,Response.success(HttpResult.Success(body)))
     *
     */
    override fun enqueue(callback: Callback<HttpResult<S>>) {
        return call.enqueue(object : Callback<S> {

            override fun onResponse(call: Call<S>, response: Response<S>) {
                val data = response.body()
                val code = response.code()
                val error = response.errorBody()

                //Http 返回[200..300).
                if (response.isSuccessful) {
                    callback.onResponse(
                        this@HttpResponseCall,
                        Response.success(HttpResult.Success(data))
                    )
                } else {
                    //http 异常
                    if (error != null && error.contentLength() > 0) {
                        val errorText = error.source().peek().readUtf8()

                        errorConverter.onFailure(
                            BusinessException(errorText)
                        )

                        callback.onResponse(
                            this@HttpResponseCall,
                            Response.success(
                                HttpResult.Failure(errorText)
                            )
                        )
                    } else {
                        //没有Error Body 的情况
                        callback.onResponse(
                            this@HttpResponseCall,
                            Response.success(
                                HttpResult.Failure("Message is empty")
                            )
                        )

                    }
                }
            }

            /**
             * 失败，还有各种Exception
             */
            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    //IO Exception
                    is IOException -> HttpResult.Failure(throwable.message.toString())

                    //业务code
                    is BusinessException -> {
                        errorConverter.onFailure(throwable)
                        HttpResult.Failure(throwable.message ?: "网络请求错误，稍后再试～")
                    }

                    //拓展
                    else -> {
                        HttpResult.Failure(throwable.message ?: "未知错误")
                    }

                }
                callback.onResponse(
                    this@HttpResponseCall,
                    Response.success(networkResponse)
                )
            }


        })
    }

    override fun execute(): Response<HttpResult<S>> {
        throw UnsupportedOperationException("HttpResponseCall doesn't support execute")
    }

    override fun isExecuted() = call.isExecuted

    override fun clone() = HttpResponseCall(call.clone(), errorConverter)

    override fun isCanceled() = call.isCanceled

    override fun cancel() = call.cancel()

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()


}
