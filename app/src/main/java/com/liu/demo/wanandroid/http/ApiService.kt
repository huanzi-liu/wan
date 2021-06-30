package com.liu.demo.wanandroid.http

import com.liu.demo.wanandroid.bean.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /**
     * 搜索热词
     */
    @GET("/hotkey/json")
    suspend fun hotKey(): DataBean<MutableList<HotKeyBean>?>

    /**
     * 首页banner
     */
    @GET("/banner/json")
    suspend fun banner(): DataBean<MutableList<BannerBean>?>

    /**
     * 主页展示列表
     */
    @GET("/article/list/{page}/json")
    suspend fun articleList(@Path("page") page: Int): DataBean<ArticleDataBean<ArticleBean>?>

    /**
     * 搜索
     */
    @POST("/article/query/{page}/json")
    suspend fun search(@Path("page") page: Int, @Query("k") k: String): DataBean<ArticleDataBean<ArticleBean>?>

    /**
     * 导航
     */
    @GET("/navi/json")
    suspend fun navigation(): DataBean<MutableList<NavigationBean>?>

    /**
     * 问答
     */
    @GET("/wenda/list/{pageId}/json")
    suspend fun question(@Path("pageId") page: Int): DataBean<ArticleDataBean<ArticleBean>?>

    /**
     * 体系
     */
    @GET("/tree/json")
    suspend fun system(): DataBean<MutableList<SystemBean>?>

    /**
     * 项目
     */
    @GET("/project/tree/json")
    suspend fun project(): DataBean<MutableList<SystemBean>?>

    /**
     * 项目列表
     */
    @GET("/project/list/{page}/json")
    suspend fun projectList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): DataBean<ArticleDataBean<ArticleBean>?>

    /**
     * 文章列表
     */
    @GET("/article/list/{page}/json")
    suspend fun articleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): DataBean<ArticleDataBean<ArticleBean>?>

    /**
     * 登录
     */
    @POST("/user/login")
    suspend fun login(
        @Query("username") name: String,
        @Query("password") password: String
    ): DataBean<Any?>

    /**
     * 注册
     */
    @POST("/user/register")
    suspend fun register(
        @Query("name") name: String,
        @Query("password") password: String,
        @Query("repassword") rePassword: String
    ): DataBean<Any?>

    /**
     * 注消
     */
    @GET("/user/logout/json")
    suspend fun logout(): DataBean<Any?>

    /**
     * 收藏站内文章
     */
    @POST("/lg/collect/{id}/json")
    suspend fun insideCollect(@Path("id") id: Int): DataBean<Any?>

    /**
     * 收藏文章列表
     */
    @GET("/lg/collect/list/{page}/json")
    suspend fun collectList(@Path("page") page: Int): DataBean<ArticleDataBean<ArticleBean>?>

    /**
     * 取消收藏(文章列表)
     */
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun insideUnCollect(@Path("id") id: Int): DataBean<Any?>

    /**
     * 個人積分
     */
    @GET("/lg/coin/userinfo/json")
    suspend fun integral():DataBean<UserInfo?>
    /**
     * 积分记录
     */
    @GET("/lg/coin/list/{page}/json")
    suspend fun integralRecoding(@Path("page") page: Int) :DataBean<ArticleDataBean<IntegralBean>?>
}