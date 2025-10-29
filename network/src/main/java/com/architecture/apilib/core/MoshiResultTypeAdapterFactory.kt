package com.architecture.apilib.core

import com.architecture.apilib.error.BusinessException
import com.squareup.moshi.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * JsonAdapter.Factory
 * Converts Java values to JSON, and JSON values to Java.
 */
class MoshiResultTypeAdapterFactory(private val httpWrapper: HttpWrapper?) : JsonAdapter.Factory {

    /**
     * HttpResultWrapper
     *
     * 假设公司各个业务服务器返回的Http Json数据格式都差不多三个大字段（名称允许自定义不同）code[int] + msg[str] +data[T]
     *
     * 根据项目自身的情况再次拓展一下让其更具有包容性
     *
     */
    interface HttpWrapper {
        fun getStatusCodeKey(): String
        fun getErrorMsgKey(): String
        fun getDataKey(): String

        //有些服务器是0代表成功，有些是200 代表成功
        fun isRequestSuccess(statusCode: Int?): Boolean
    }

    override fun create(
        type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi
    ): JsonAdapter<*>? {

        val rawType = type.rawType

        if (rawType != HttpResult::class.java) return null

        val dataType: Type =
            (type as? ParameterizedType)?.actualTypeArguments?.firstOrNull() ?: return null

        val dataTypeAdapter = moshi.nextAdapter<Any>(this, dataType, annotations)

        //Result<T> 范型解析出来
        return ResultTypeAdapter(dataTypeAdapter, httpWrapper)
    }


    /**
     * 返回请求需求的那个T
     */
    class ResultTypeAdapter<T>(
        private val dataTypeAdapter: JsonAdapter<T>, private val httpWrapper: HttpWrapper?
    ) : JsonAdapter<T>() {

        /**
         * Decodes a nullable instance of type T from the given reader.
         */
        override fun fromJson(reader: JsonReader): T? {

            if (httpWrapper != null) {
                reader.beginObject()

                //一般都是code +msg + result/data
                var code: Int? = null
                var msg: String? = null
                var data: Any? = null

                while (reader.hasNext()) {
                    when (reader.nextName()) {
                        //根据不同服务器后台HTTP 报文字段 解析映射出code +msg + data
                        httpWrapper.getStatusCodeKey() -> code =
                            reader.nextString().toIntOrNull()

                        httpWrapper.getErrorMsgKey() -> msg = reader.nextString()
                        httpWrapper.getDataKey() -> data = dataTypeAdapter.fromJson(reader)
                        else -> reader.skipValue()
                    }
                }

                reader.endObject()

                if (httpWrapper.isRequestSuccess(code)) {
                    return data as T
                } else {
                    throw BusinessException(msg)
                }

            } else {
                //envelope == null 不是标准的Code + msg +data 也没关系
                return dataTypeAdapter.fromJson(reader) as T
            }

        }

        override fun toJson(writer: JsonWriter, value: T?) {

        }

    }

}
