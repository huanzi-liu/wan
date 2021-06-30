package com.liu.demo.wanandroid.bean

import java.io.Serializable

data class DataBean<T>(val errorCode:Int,val errorMsg:String? =null,val data:T?) :BaseBean(), Serializable
