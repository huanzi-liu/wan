package com.liu.demo.wanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.liu.demo.wanandroid.base.BaseViewModel
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.ArticleDataBean
import com.liu.demo.wanandroid.bean.DataBean
import com.liu.demo.wanandroid.repository.QuestionRepository
import kotlinx.coroutines.async

class QuestionViewModel(app: Application) : BaseViewModel(app) {
    val rep: QuestionRepository by lazy { QuestionRepository() }

    var dataBean: MutableLiveData<ArticleDataBean<ArticleBean>?> = MutableLiveData()
    var dataMsg: MutableLiveData<String> = MutableLiveData()
    var page = 0
    var pageCont = 1
    var isRefresh = false

    fun getQuestion(isRefresh: Boolean) = launchUI {
        async {
            this@QuestionViewModel.isRefresh =isRefresh
            if (isRefresh) {
                page = 0
            } else {
                page++
            }
            if (page <= pageCont) {
                rep.getQuestion(page).apply {
                    responseResult(this, {
                        dataBean.value = this@apply.data
                        pageCont = this@apply.data?.pageCount ?: 1
                    }, {
                        dataMsg.value = this@apply.errorMsg
                    })
                }
            }
        }.await()
    }
}