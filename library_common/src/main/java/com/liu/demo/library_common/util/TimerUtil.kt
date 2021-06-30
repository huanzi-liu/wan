package com.liu.demo.library_common.util

import android.text.TextUtils
import com.blankj.ALog
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object TimerUtil {

    var disposableMap = HashMap<String, Any>()

    fun hasTimer(name: String): Boolean = disposableMap.containsKey(name)

    /**
     * xx后执行next(默认毫秒)
     */
    fun timer(
        seconds: Long,
        timeUnit: TimeUnit = TimeUnit.MICROSECONDS,
        name: String,
        next: IListener?
    ) {
        if (disposableMap.containsKey(name)) {
            ALog.i("重复执行，此次取消")
            return
        }
        Observable.timer(seconds, timeUnit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {
                    disposableMap[name] = d
                }

                override fun onNext(t: Long) {
                    if (next != null) {
                        cancel(name)
                        next.onComplete()
                    }
                }

                override fun onError(e: Throwable) {
                    cancel(name)
                }

                override fun onComplete() {
                    cancel(name)
                }

            })
    }

    /**
     * 每xx后执行next（默认毫秒）
     */
    fun intervalTimer(
        seconds: Long,
        timeUnit: TimeUnit = TimeUnit.MICROSECONDS,
        name: String,
        next: INext
    ) {
        if (disposableMap.containsKey(name)) {
            ALog.i("重复执行，此次取消")
            return
        }
        Observable.interval(seconds, timeUnit)
//            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {
                    disposableMap[name] = d
                }

                override fun onNext(t: Long) {
                    next.doNext(t)
                }

                override fun onError(e: Throwable) {
                    cancel(name)
                }

                override fun onComplete() {
                    cancel(name)
                }

            })

    }


    fun cancel(vararg names: String) {
        if (names.isNotEmpty()) {
            for (name in names) {
                val disposable: Disposable? = disposableMap[name]?.let { it as Disposable }
                disposableMap.remove(name)
                if (disposable != null) {
                    if (!disposable.isDisposed) {
                        disposable.dispose()
                    }
                }
            }
        }
    }

    interface INext {
        fun doNext(number: Long)
    }

    interface ITimer {
        fun doNext(number: Long, complete: Boolean)
    }

    interface IListener {

        fun onComplete()
    }
}