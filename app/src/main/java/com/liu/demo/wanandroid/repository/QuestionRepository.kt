package com.liu.demo.wanandroid.repository

import com.liu.demo.wanandroid.base.BaseRepository
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.ArticleDataBean
import com.liu.demo.wanandroid.bean.DataBean
import com.liu.demo.wanandroid.http.Retrofit

class QuestionRepository: BaseRepository() {

    suspend fun getQuestion(page: Int): DataBean<ArticleDataBean<ArticleBean>?> {
        return apiCall {
            Retrofit.getApi().question(page)
        }
    }

}