package com.liu.demo.wanandroid.bean

import java.io.Serializable

data class HotKeyBean(
    var id: Int,
    var link: String = "",
    var name: String = "",
    var order: Int,
    var visible: Int
) : BaseBean(), Serializable
