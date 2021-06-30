package com.liu.demo.wanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.liu.demo.wanandroid.base.BaseViewModel
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.ArticleDataBean
import com.liu.demo.wanandroid.bean.DataBean
import com.liu.demo.wanandroid.bean.SystemBean
import com.liu.demo.wanandroid.repository.ProjectRepository
import com.liu.demo.wanandroid.repository.QuestionRepository
import com.liu.demo.wanandroid.repository.SystemRepository
import kotlinx.coroutines.async

class ProjectViewModel(app: Application) : BaseViewModel(app) {
    val rep: ProjectRepository by lazy { ProjectRepository() }

    var dataBean: MutableLiveData<MutableList<SystemBean>?> = MutableLiveData()
    var dataMsg: MutableLiveData<String> = MutableLiveData()
    var dataBeanList: MutableLiveData<ArticleDataBean<ArticleBean>?> = MutableLiveData()
    var dataMsgList: MutableLiveData<String> = MutableLiveData()
    var page = 1
    var pageCont = 2
    var isRefresh = false

    fun getProject() = launchUI {
        async {
            rep.getProject().apply {
                responseResult(this, {
                    dataBean.value = this@apply.data
                }, {
                    dataMsg.value = this@apply.errorMsg
                })
            }
        }.await()
    }

    fun getProjectList(isRefresh: Boolean, cid: Int) = launchUI {
        async {
            this@ProjectViewModel.isRefresh =isRefresh
            if (isRefresh) {
                page = 1
            } else {
                page++
            }
            if (page <= pageCont) {
                rep.getProjectList(page, cid).apply {
                    responseResult(this, {
                        dataBeanList.value = this@apply.data
                        pageCont = this@apply.data?.pageCount ?: 2
                    }, {
                        dataMsgList.value = this@apply.errorMsg
                    })
                }
            }
        }.await()
    }
}