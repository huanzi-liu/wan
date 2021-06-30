package com.liu.demo.wanandroid.bean

import java.io.Serializable

data class UserInfo(
    var coinCount: Int,
    var level: Int,
    var nickname: String? = "",
    var rank: String? = "",
    var userId: Int,
    var username: String? = ""
) : BaseBean(), Serializable
