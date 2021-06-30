package com.liu.demo.wanandroid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.liu.demo.wanandroid.base.BaseViewModel
import com.liu.demo.wanandroid.bean.*
import com.liu.demo.wanandroid.helper.SingleLiveEvent
import com.liu.demo.wanandroid.repository.HomeRepository
import kotlinx.coroutines.async

class HomeViewModel(app: Application) : BaseViewModel(app) {

    val rep: HomeRepository by lazy { HomeRepository() }

    var hotKeyData: MutableLiveData<MutableList<HotKeyBean>?> = MutableLiveData()
    var hotKeyMsg: MutableLiveData<String?> = MutableLiveData()
    var bannerData: MutableLiveData<MutableList<BannerBean>?> = MutableLiveData()
    var articleDataBean: MutableLiveData<ArticleDataBean<ArticleBean>?> = MutableLiveData()
    var articleDataMsg: MutableLiveData<String?> = MutableLiveData()
    var page = 0
    var pageCont = 1
    var isRefresh = false

    var searchData:MutableLiveData<ArticleDataBean<ArticleBean>?> = MutableLiveData()
    var searchMsg:MutableLiveData<String?> = MutableLiveData()

    var userInfoData:SingleLiveEvent<UserInfo?> = SingleLiveEvent()
    var userInfoMsg:SingleLiveEvent<String?> = SingleLiveEvent()
    var userInfoCode:SingleLiveEvent<Int> = SingleLiveEvent()

    var recodingData:MutableLiveData<MutableList<IntegralBean>?> = MutableLiveData()
    var recodingMsg:SingleLiveEvent<String?> = SingleLiveEvent()

    fun getHotKey() = launchUI {
        async {
            rep.getHotKey().apply {
                responseResult(this, {
                    hotKeyData.value = this@apply.data
                }, {
                    hotKeyMsg.value = this@apply.errorMsg
                })
            }
        }
    }

    fun getBanner() = launchUI {
        async {
            rep.getBanner().apply {
                responseResult(
                    this,
                    {
                        this@apply.data.also { bannerData.value = it }
                    }, {
                        Log.i("TAG_ERROR", this@apply.errorMsg.toString())
                    })
            }
        }.await()
    }

    fun getArticle(isRefresh: Boolean) = launchUI {
        async {
            this@HomeViewModel.isRefresh = isRefresh
            if (isRefresh) {
                page = 0
            } else {
                page++
            }
            if (page <= pageCont) {
                rep.getArticle(page).apply {
                    responseResult(this,
                        {
                            this@apply.data.also { articleDataBean.value = it }
                            pageCont = this@apply.data?.pageCount ?: 1
                        },
                        {
                            articleDataMsg.value = this@apply.errorMsg
                            Log.i("TAG_ERROR", this@apply.errorMsg.toString())
                        })
                }
            }
        }.await()
    }

    fun getSearch(k:String,isRefresh: Boolean)=launchUI {
        async {
            this@HomeViewModel.isRefresh = isRefresh
            if (isRefresh) {
                page = 0
            } else {
                page++
            }
            if (page <= pageCont) {
                rep.getSearch(page,k).apply {
                    responseResult(this,
                        {
                            this@apply.data.also { searchData.value = it }
                            pageCont = this@apply.data?.pageCount ?: 1
                        },
                        {
                            searchMsg.value = this@apply.errorMsg
                            Log.i("TAG_ERROR", this@apply.errorMsg.toString())
                        })
                }
            }
        }.await()
    }

    fun getIntegral() = launchUI {
        async {
            rep.getIntegral().apply {
                responseResult(this,{
                                    userInfoData.value = this@apply.data
                },{
                    userInfoMsg.value = this@apply.errorMsg
                })
                userInfoCode.value = this.errorCode
            }
        }.await()
    }

    fun getRecoding(isRefresh: Boolean) =launchUI {
        async {
            this@HomeViewModel.isRefresh = isRefresh
            if (isRefresh) {
                page = 1
            } else {
                page++
            }
            if (page <= pageCont) {
                rep.getIntegralRecoding(page).apply {
                    responseResult(this,
                        {
                            this@apply.data.also { recodingData.value = it?.datas }
                            pageCont = this@apply.data?.pageCount ?: 1
                        },
                        {
                            recodingMsg.value = this@apply.errorMsg
                        })
                }
            }
        }
    }
}