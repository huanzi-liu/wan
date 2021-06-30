package com.liu.demo.wanandroid.http

import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {

    private const val READ_TIME = 8L
    private const val WRITE_TIME = 8L
    private const val CONNECT_TIME = 8L
    var cookieStore:HashMap<String,List<Cookie>> = HashMap()

    private fun httpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(READ_TIME, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor {
                Log.i("SS", it)
           }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .cookieJar(object :CookieJar{
                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    var cookies = cookieStore[url.host]
                    return cookies ?: ArrayList()
                }

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    cookieStore[url.host] = cookies
                }

            })
            .build()

    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().client(httpClient()).baseUrl("https://www.wanandroid.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CallAdapterFactory())
            .build()
    }

    fun getApi(): ApiService = retrofit.create(ApiService::class.java)
}