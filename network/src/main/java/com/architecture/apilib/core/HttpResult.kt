package com.architecture.apilib.core

/***
 * 枚举
 *
 * HTTP 返回常见的状态码
 * 200 OK                        //客户端请求成功
 * 400 Bad Request               //客户端请求有语法错误，不能被服务器所理解
 * 401 Unauthorized              //请求未经授权，这个状态代码必须和WWW-Authenticate报头域一起使用
 * 403 Forbidden                 //服务器收到请求，但是拒绝提供服务
 * 404 Not Found                 //请求资源不存在，eg：输入了错误的URL
 * 500 Internal Server Error     //服务器发生不可预期的错误
 * 503 Server Unavailable        //服务器当前不能处理客户端的请求，一段时间后可能恢复正常
 *
 */
sealed class HttpResult<out T : Any> {

    //200-300 就是Success，body 就是业务上真实的成功时候需要的数据
    data class Success<T : Any>(val data: T) : HttpResult<T>()

    //各种失败，异常全部到这里来吧
    data class Failure(val message: String) : HttpResult<Nothing>()

}
