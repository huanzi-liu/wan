package com.liu.demo.wanandroid.repository

import com.liu.demo.wanandroid.base.BaseRepository
import com.liu.demo.wanandroid.bean.DataBean
import com.liu.demo.wanandroid.http.Retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

class LoginRegisterRepository:BaseRepository() {
    suspend fun login(name:String,password:String):DataBean<Any?>{
            return apiCall {
                Retrofit.getApi().login(name, password)
            }
    }

    suspend fun register(name: String, password: String, rePassword: String): DataBean<Any?> {
        return apiCall {
            Retrofit.getApi().register(name, password, rePassword)
        }
    }

    suspend fun logout(): DataBean<Any?> {
        return apiCall {
            Retrofit.getApi().logout()
        }
    }

//    suspend fun responseResult(
//        response: DataBean<*>,
//        success: suspend CoroutineScope.() -> Unit,
//        failure: CoroutineScope.() -> Unit
//    ) {
//        coroutineScope{
//            if (response.errorCode == 0) {
//                success()
//            }else{
//                failure()
//            }
//        }
//
//    }
}