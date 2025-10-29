package com.architecture.apilib.error

import java.lang.RuntimeException

/**
 * 业务的错误，错误码+ 错误String
 */
class BusinessException(message: String?) : RuntimeException(message)