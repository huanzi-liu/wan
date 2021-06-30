package com.liu.demo.wanandroid.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.Exception

class PublicMutableLiveData<T>:MutableLiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        hook(observer)
    }

    private fun hook(observer: Observer<in T>){
        val liveDataClass = LiveData::class.java
        try {

            val observers = liveDataClass.getDeclaredField("observers")
            observers.isAccessible = true
            val observersObject = observers.get(this) ?: return

            val observersClass = observersObject.javaClass
            val methodGet = observersClass.getDeclaredMethod("get",Object::class.java)
            methodGet.isAccessible = true
            val entry = methodGet.invoke(observersObject,observer)
            if ((entry !is Map.Entry<*,*>)){
                    return
            }
            val lifecycleBoundObserver = (entry as Map.Entry<*,*>).value
            val observerWrapper = lifecycleBoundObserver?.javaClass?.superclass ?: return
            val lastVersionField = observerWrapper.getDeclaredField("lastVersion")
            lastVersionField.isAccessible = true

            val versionMethod = liveDataClass.getDeclaredMethod("getVersion")
            versionMethod.isAccessible = true
            val version = versionMethod.invoke(this)
            lastVersionField.set(lifecycleBoundObserver,version)

            observers.isAccessible = false
            methodGet.isAccessible = false
            lastVersionField.isAccessible = false
            versionMethod.isAccessible = false

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}