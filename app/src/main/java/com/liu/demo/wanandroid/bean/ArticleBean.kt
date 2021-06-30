package com.liu.demo.wanandroid.bean

import java.io.Serializable

data class ArticleDataBean<T>(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: MutableList<T>? = null
) : BaseBean(), Serializable

data class ArticleBean(
    var apkLink: String = "",
    var audit: String = "",
    var author: String = "",
    var canEdit: Boolean = false,
    var chapterId: String = "",
    var chapterName: String = "",
    var collect: Boolean = false,
    var courseId: String = "",
    var desc: String = "",
    var descMd: String = "",
    var envelopePic: String = "",
    var top: Boolean = false,
    var fresh: Boolean = false,
    var host: String = "",
    var id: Int,
    var link: String = "",
    var niceDate: String = "",
    var niceShareDate: String = "",
    var origin: String = "",
    var prefix: String = "",
    var projectLink: String = "",
    var publishTime: String = "",
    var realSuperChapterId: String = "",
    var selfVisible: String = "",
    var shareDate: String = "",
    var shareUser: String = "",
    var superChapterId: String = "",
    var superChapterName: String = "",
    var tags: MutableList<ArticleTagBean>? = null,
    var title: String = "",
    var type: String = "",
    var userId: String = "",
    var visible: String = "",
    var zan: String = ""
) : BaseBean(), Serializable


data class ArticleTagBean(var name: String?, var url: String?) : BaseBean(), Serializable