package com.liu.demo.wanandroid.bean

import java.io.Serializable

data class IntegralBean(
    var coinCount: Int,
    var date: Long,
    var desc: String?="",
    var id: Int,
    var reason: String?="",
    var type: Int,
    var userId: Int,
    var userName: String?=""
) : BaseBean(), Serializable
