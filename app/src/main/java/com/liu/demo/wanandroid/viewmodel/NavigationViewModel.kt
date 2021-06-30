package com.liu.demo.wanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.liu.demo.wanandroid.base.BaseViewModel
import com.liu.demo.wanandroid.bean.NavigationBean
import com.liu.demo.wanandroid.repository.NavigationRepository
import kotlinx.coroutines.async

class NavigationViewModel(app: Application) : BaseViewModel(app) {

    val rep: NavigationRepository by lazy { NavigationRepository() }
    var dataBean: MutableLiveData<MutableList<NavigationBean>?> = MutableLiveData()
    var dataMsg: MutableLiveData<String> = MutableLiveData()

    fun getNavigation() = launchUI {
        async {
            rep.getNavigation().apply {
                responseResult(this, {
                    dataBean.value = this@apply.data
                }, {
                    dataMsg.value = this@apply.errorMsg
                })
            }
        }.await()
    }
}