package com.liu.demo.wanandroid.repository

import com.liu.demo.wanandroid.base.BaseRepository
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.ArticleDataBean
import com.liu.demo.wanandroid.bean.DataBean
import com.liu.demo.wanandroid.bean.SystemBean
import com.liu.demo.wanandroid.http.Retrofit

class SystemRepository: BaseRepository() {

    suspend fun getSystem(): DataBean<MutableList<SystemBean>?> {
        return apiCall {
            Retrofit.getApi().system()
        }
    }

}