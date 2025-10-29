package com.architecture.apilib.interceptor;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 请求返回拦截器
 */
public class CommonResponseInterceptor implements Interceptor {
    private static final String TAG = "ResponseInterceptor";

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();

        Response response = chain.proceed(chain.request());
        String rawJson = response.body() == null ? "" : response.body().string();
        //请求响应时长
        Log.d(TAG, "requestTime=" + (System.currentTimeMillis() - requestTime));

        return response;
    }
}
