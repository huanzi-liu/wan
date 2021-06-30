package com.liu.demo.wanandroid.bean

import java.io.Serializable

/**
 * {"desc":"扔物线",
"id":29,
"imagePath":"https://wanandroid.com/blogimgs/31c2394c-b07c-4699-afd1-95aa7a3bebc6.png",
"isVisible":1,
"order":0,
"title":"View 嵌套太深会卡？来用 Jetpack Compose，随便套&mdash;&mdash;Compose 的 Intrinsic Measurement",
"type":0,
"url":"https://www.bilibili.com/video/BV1ZA41137gr" }
 */
data class BannerBean(
    var desc: String?,
    var id: Int,
    var imagePath: String?,
    var isVisible: Int,
    var order: Int,
    var title: String?,
    var type: Int,
    var url: String?
) :BaseBean(),Serializable
