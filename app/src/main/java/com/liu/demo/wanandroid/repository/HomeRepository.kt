package com.liu.demo.wanandroid.repository

import com.liu.demo.wanandroid.base.BaseRepository
import com.liu.demo.wanandroid.bean.*
import com.liu.demo.wanandroid.http.Retrofit

class HomeRepository : BaseRepository() {

    suspend fun getHotKey() :DataBean<MutableList<HotKeyBean>?>{
        return apiCall { Retrofit.getApi().hotKey() }
    }

    suspend fun getBanner(): DataBean<MutableList<BannerBean>?> {
        return apiCall {
            Retrofit.getApi().banner()
        }
    }

    suspend fun getArticle(page:Int): DataBean<ArticleDataBean<ArticleBean>?> {
        return apiCall { Retrofit.getApi().articleList(page) }
    }

    suspend fun getSearch(page: Int, k: String): DataBean<ArticleDataBean<ArticleBean>?> {
        return apiCall { Retrofit.getApi().search(page, k) }
    }

    suspend fun getIntegral(): DataBean<UserInfo?> {
        return apiCall { Retrofit.getApi().integral() }
    }

    suspend fun getIntegralRecoding(page: Int): DataBean<ArticleDataBean<IntegralBean>?> {
        return apiCall { Retrofit.getApi().integralRecoding(page) }
    }
}