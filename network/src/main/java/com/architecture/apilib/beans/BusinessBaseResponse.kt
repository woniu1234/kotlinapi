package com.architecture.apilib.beans

import com.squareup.moshi.Json

/**
 * 一般都是 code + msg +data(/result)
 */
open class BusinessBaseResponse {

    @Json(name = "code")
    val code: Int = 0

    @Json(name = "message")
    val message: String = ""

}