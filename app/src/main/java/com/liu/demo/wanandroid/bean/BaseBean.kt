package com.liu.demo.wanandroid.bean

import com.google.gson.Gson
import java.io.Serializable

open class BaseBean() :Serializable{
    override fun toString(): String {
        return Gson().toJson(this)
    }
}