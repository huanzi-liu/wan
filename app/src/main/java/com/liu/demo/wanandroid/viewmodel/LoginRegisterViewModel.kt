package com.liu.demo.wanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.liu.demo.wanandroid.base.BaseViewModel
import com.liu.demo.wanandroid.repository.LoginRegisterRepository
import kotlinx.coroutines.async

class LoginRegisterViewModel(app: Application) : BaseViewModel(app) {

    val rep: LoginRegisterRepository by lazy { LoginRegisterRepository() }

    var loginData: MutableLiveData<Any?> = MutableLiveData()
    var registerData: MutableLiveData<Any?> = MutableLiveData()
    var logoutData: MutableLiveData<Any?> = MutableLiveData()
    var loginMsg: MutableLiveData<String?> = MutableLiveData()
    var registerMsg: MutableLiveData<String?> = MutableLiveData()
    var logoutMsg: MutableLiveData<String?> = MutableLiveData()


    fun login(name: String, password: String) = launchUI {
        async {
            rep.login(name, password).apply {
                responseResult(this, {
                    loginData.value = this@apply.data
                }, {
                    loginMsg.value = this@apply.errorMsg
                })
            }
        }.await()
    }

    fun register(name: String, password: String,rePassword:String) = launchUI {
        async {
            rep.register(name, password,rePassword).apply {
                responseResult(this, {
                    registerData.value = this@apply.data
                }, {
                    registerMsg.value = this@apply.errorMsg
                })
            }
        }.await()
    }

    fun logout() = launchUI {
        async {
            rep.logout().apply {
                responseResult(this, {
                    logoutData.value = this@apply.data
                }, {
                    logoutMsg.value = this@apply.errorMsg
                })
            }
        }.await()
    }
}