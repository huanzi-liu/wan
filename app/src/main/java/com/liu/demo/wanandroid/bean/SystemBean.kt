package com.liu.demo.wanandroid.bean

import java.io.Serializable

data class SystemBean(
    val children: MutableList<SystemBean>? = null,
    var childrenSelectPosition: Int = 0,
    val courseId: String = "",
    val id: Int,
    val name: String = "",
    val order: String = "",
    val parentChapterId: String = "",
    val userControlSetTop: String = "",
    val visible: String = ""
):BaseBean(),Serializable
