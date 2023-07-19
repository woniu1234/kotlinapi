package com.example.kotlinapidemo.api

import com.architecture.apilib.core.MoshiResultTypeAdapterFactory

object MainApi : CommonApi() {

    fun getApi(): IMainApi {
        return getService(IMainApi::class.java)
    }

    override fun getHttpWrapperHandler(): MoshiResultTypeAdapterFactory.HttpWrapper {
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
                return statusCode == 200
            }

        }
    }

}