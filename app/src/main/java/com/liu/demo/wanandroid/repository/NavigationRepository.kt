package com.liu.demo.wanandroid.repository

import com.liu.demo.wanandroid.base.BaseRepository
import com.liu.demo.wanandroid.bean.DataBean
import com.liu.demo.wanandroid.bean.NavigationBean
import com.liu.demo.wanandroid.http.Retrofit

class NavigationRepository : BaseRepository() {

    suspend fun getNavigation():DataBean<MutableList<NavigationBean>?> {
        return apiCall {
            Retrofit.getApi().navigation()
        }

    }
}