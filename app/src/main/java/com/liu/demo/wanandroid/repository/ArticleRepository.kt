package com.liu.demo.wanandroid.repository

import com.liu.demo.wanandroid.base.BaseRepository
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.ArticleDataBean
import com.liu.demo.wanandroid.bean.BannerBean
import com.liu.demo.wanandroid.bean.DataBean
import com.liu.demo.wanandroid.http.Retrofit

class ArticleRepository : BaseRepository() {

    suspend fun getArticleList(page:Int,cid:Int): DataBean<ArticleDataBean<ArticleBean>?> {
        return apiCall { Retrofit.getApi().articleList(page,cid) }
    }

    suspend fun insideCollect(id: Int) :DataBean<Any?>{
        return apiCall { Retrofit.getApi().insideCollect(id) }
    }
    suspend fun insideUnCollect(id: Int) :DataBean<Any?>{
        return apiCall { Retrofit.getApi().insideUnCollect(id) }
    }

    suspend fun getCollectList(page:Int): DataBean<ArticleDataBean<ArticleBean>?> {
        return apiCall { Retrofit.getApi().collectList(page) }
    }
}