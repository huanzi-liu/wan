package com.liu.demo.wanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.ALog
import com.liu.demo.wanandroid.base.BaseViewModel
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.ArticleDataBean
import com.liu.demo.wanandroid.helper.PublicMutableLiveData
import com.liu.demo.wanandroid.helper.SingleLiveEvent
import com.liu.demo.wanandroid.http.Retrofit
import com.liu.demo.wanandroid.repository.ArticleRepository
import kotlinx.coroutines.async

class ArticleViewModel(app: Application) : BaseViewModel(app) {

    val rep: ArticleRepository by lazy { ArticleRepository() }

    var articleDataBean: MutableLiveData<ArticleDataBean<ArticleBean>?> = MutableLiveData()
    var articleDataMsg: MutableLiveData<String?> = MutableLiveData()
//    var collectDataBean: MutableLiveData<Boolean> = MutableLiveData()
//    var unCollectDataBean: MutableLiveData<Boolean> = MutableLiveData()
////    var collectDataBean: MutableLiveData<Any?> = MutableLiveData()
//    var collectDataMsg: MutableLiveData<String?> = MutableLiveData()
    var collectDataCode: MutableLiveData<String?> = MutableLiveData()
////    var unCollectDataBean: MutableLiveData<Any?> = MutableLiveData()
//    var unCollectDataMsg: MutableLiveData<String?> = MutableLiveData()
    var unCollectDataCode: MutableLiveData<String?> = MutableLiveData()

//    val collectDataBean: PublicMutableLiveData<Boolean> = PublicMutableLiveData()
//    val unCollectDataBean: PublicMutableLiveData<Boolean> = PublicMutableLiveData()
//    val collectDataMsg: PublicMutableLiveData<String?> = PublicMutableLiveData()
//    val collectDataCode: PublicMutableLiveData<String?> = PublicMutableLiveData()
//    val unCollectDataMsg: PublicMutableLiveData<String?> = PublicMutableLiveData()
//    val unCollectDataCode: PublicMutableLiveData<String?> = PublicMutableLiveData()

    private val _collectDataBean = MutableLiveData<Boolean>()
    val collectDataBean: LiveData<Boolean> = _collectDataBean
    private val _collectDataMsg = MutableLiveData<String?>()
    val collectDataMsg: LiveData<String?> = _collectDataMsg
//    private val _collectDataCode = MutableLiveData<String?>()
//    val collectDataCode: LiveData<String?> = _collectDataCode
    private val _unCollectDataBean = MutableLiveData<Boolean>()
    val unCollectDataBean: LiveData<Boolean> = _unCollectDataBean
    private val _unCollectDataMsg = MutableLiveData<String?>()
    val unCollectDataMsg: LiveData<String?> = _unCollectDataMsg
//    private val _unCollectDataCode = MutableLiveData<String?>()
//    val unCollectDataCode: LiveData<String?> = _unCollectDataCode

    var page = 0
    var pageCont = 1
    var isRefresh = false

    var collectListData: MutableLiveData<ArticleDataBean<ArticleBean>?> = MutableLiveData()
    var collectListMsg: MutableLiveData<String?> = MutableLiveData()

    fun getArticleList(isRefresh: Boolean, cid: Int) = launchUI {
       val job= async {
            this@ArticleViewModel.isRefresh = isRefresh
            if (isRefresh) {
                page = 0
            } else {
                page++
            }
            if (page <= pageCont) {
                apiCall { Retrofit.getApi().articleList(page,cid) }.apply {
                    responseResult(this,
                        {
                            this@apply.data.also { articleDataBean.value = it }
                            pageCont = this@apply.data?.pageCount ?: 1
                        },
                        {
                            articleDataMsg.value = this@apply.errorMsg
                        })
                }
//                rep.getArticleList(page, cid).apply {
//                    responseResult(this,
//                        {
//                            this@apply.data.also { articleDataBean.value = it }
//                            pageCont = this@apply.data?.pageCount ?: 1
//                        },
//                        {
//                            articleDataMsg.value = this@apply.errorMsg
//                        })
//                }
            }
        }
           job.await()
    }

    fun insideCollect(id: Int) {
     launchUI {
        val job =async {
//            rep.insideCollect(id).apply {
                apiCall { Retrofit.getApi().insideCollect(id) }.apply {
                responseResult(this, {
//                    collectDataBean.value = this@apply.data
                    _collectDataBean.value = true
                }, {
                    _collectDataMsg.value = this@apply.errorMsg
                    collectDataCode.value = this@apply.errorCode.toString()
                })
            }
        }
            job.await()
     }
    }
    fun insideUnCollect(id: Int) {
        launchUI {
            val job = async {
//            rep.insideUnCollect(id).apply {
                apiCall { Retrofit.getApi().insideUnCollect(id) }.apply {
                    responseResult(this, {
//                    unCollectDataBean.value = this@apply.data
                        ALog.i("----${this@apply}")
                        _unCollectDataBean.value = true
                    }, {
                        _unCollectDataMsg.value = this@apply.errorMsg
                        unCollectDataCode.value = this@apply.errorCode.toString()
                    })
                }
            }
            job.await()
        }
    }

    fun getCollectList(isRefresh: Boolean) = launchUI {
        async {
            this@ArticleViewModel.isRefresh = isRefresh
            if (isRefresh) {
                page = 0
            } else {
                page++
            }
            if (page <= pageCont) {
                apiCall { Retrofit.getApi().collectList(page) }.apply {
                    responseResult(this,
                        {
                            this@apply.data.also { collectListData.value = it }
                            pageCont = this@apply.data?.pageCount ?: 1
                        },
                        {
                            collectListMsg.value = this@apply.errorMsg
                        })
                }
//                rep.getCollectList(page).apply {
//                    responseResult(this,
//                        {
//                            this@apply.data.also { collectListData.value = it }
//                            pageCont = this@apply.data?.pageCount ?: 1
//                        },
//                        {
//                            collectListMsg.value = this@apply.errorMsg
//                        })
//                }
            }
        }.await()
    }
}