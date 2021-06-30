package com.liu.demo.wanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.liu.demo.wanandroid.base.BaseViewModel
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.ArticleDataBean
import com.liu.demo.wanandroid.bean.DataBean
import com.liu.demo.wanandroid.bean.SystemBean
import com.liu.demo.wanandroid.repository.QuestionRepository
import com.liu.demo.wanandroid.repository.SystemRepository
import kotlinx.coroutines.async

class SystemViewModel(app: Application) : BaseViewModel(app) {
    val rep: SystemRepository by lazy { SystemRepository() }

    var dataBean: MutableLiveData<MutableList<SystemBean>?> = MutableLiveData()
    var dataMsg: MutableLiveData<String> = MutableLiveData()

    fun getSystem() = launchUI {
        async {
            rep.getSystem().apply {
                responseResult(this, {
                    dataBean.value = this@apply.data
                }, {
                    dataMsg.value = this@apply.errorMsg
                })
            }
        }.await()
    }
}