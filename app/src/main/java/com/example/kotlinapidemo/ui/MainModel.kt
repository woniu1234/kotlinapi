package com.example.kotlinapidemo.ui

import android.util.Log
import com.architecture.apilib.core.HttpResult
import com.architecture.apilib.core.MoshiUtils
import com.example.kotlinapidemo.api.MainApi
import com.example.kotlinapidemo.base.StateLiveData
import com.example.kotlinapidemo.beans.TestDataBean

class MainModel {

    suspend fun getTestData(data: StateLiveData<List<TestDataBean>>) {

        when (val result = MainApi.getApi().testApiData()) {
            is HttpResult.Success -> {
                //成功
                data.postSuccess(result.data)
                Log.e("Success", MoshiUtils.toJson(result.data))
            }

            is HttpResult.Failure -> {
                Log.e(
                    "HttpResult.Failure",
                    "错误编码：" + result.code + "  错误信息：" + result.message
                )
            }

        }
    }

}