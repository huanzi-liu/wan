package com.liu.demo.wanandroid.repository

import com.liu.demo.wanandroid.base.BaseRepository
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.ArticleDataBean
import com.liu.demo.wanandroid.bean.DataBean
import com.liu.demo.wanandroid.bean.SystemBean
import com.liu.demo.wanandroid.http.Retrofit

class ProjectRepository: BaseRepository() {

    suspend fun getProject(): DataBean<MutableList<SystemBean>?> {
        return apiCall {
            Retrofit.getApi().project()
        }
    }

    suspend fun getProjectList(page: Int, cid: Int): DataBean<ArticleDataBean<ArticleBean>?> {
        return apiCall {
            Retrofit.getApi().projectList(page, cid)
        }
    }

}