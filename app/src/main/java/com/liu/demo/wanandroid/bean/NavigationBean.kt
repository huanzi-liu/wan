package com.liu.demo.wanandroid.bean

import java.io.Serializable

data class NavigationBean(val name:String,val cid:Int,val articles:MutableList<ArticleBean>):BaseBean(),Serializable
