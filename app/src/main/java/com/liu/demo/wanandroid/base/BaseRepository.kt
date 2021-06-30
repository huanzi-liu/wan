package com.liu.demo.wanandroid.base

import com.google.gson.JsonParseException
import com.liu.demo.wanandroid.bean.BaseBean
import com.liu.demo.wanandroid.bean.DataBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

open class BaseRepository {
    companion object {
        const val ERROR_CODE_NOTWORK = 9999
        const val ERROR_CODE_NOTWORK_POOR = 9998
        const val ERROR_CODE_PARSE = 9997
        const val ERROR_CODE_UNKNOWN = 9996
        const val ERROR_CODE_TOKEN = 403
        const val ERROR_CODE_NOT_REGISTER = 9994

        const val ERROR_MSG_WORK = "当前网络环境不佳"
        const val ERROR_MSG_PARSE = "解析错误"
        const val ERROR_MSG = "网络数据获取错误"
    }

    suspend fun <T> apiCall(call: suspend () -> DataBean<T>): DataBean<T> {
        return withContext(Dispatchers.IO) {
            try {
                call.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
                handleCallException(e)
            }
        }.apply {
            val code = this.errorCode
            if (code != 0) {
                handleCallException<T>(Exception())
            }
        }
    }

    private fun <T> handleCallException(exception: Exception): DataBean<T> {
        return when (exception) {
            is JSONException, is JsonParseException, is ParseException -> {
                DataBean(ERROR_CODE_PARSE, ERROR_MSG_PARSE, exception.printStackTrace() as T)
            }
            is ConnectException, is UnknownHostException -> {
                DataBean(ERROR_CODE_NOTWORK, ERROR_MSG_WORK, exception.printStackTrace() as T)
            }
            is SocketTimeoutException -> {
                DataBean(ERROR_CODE_NOTWORK_POOR, ERROR_MSG_WORK, exception.printStackTrace() as T)
            }
            else -> {
                DataBean(ERROR_CODE_UNKNOWN, ERROR_MSG, exception.printStackTrace() as T)
            }
        }
    }

    suspend fun responseResultRepository(
        response: DataBean<*>,
        success: suspend CoroutineScope.() -> Unit,
        failure: CoroutineScope.() -> Unit
    ) {
        coroutineScope{
            if (response.errorCode == 0) {
                success()
            }else{
                failure()
            }
        }

    }

}