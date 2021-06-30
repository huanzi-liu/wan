package com.liu.demo.library_common.util

import android.widget.Toast
import com.liu.demo.library_common.base.BaseApplicationApp

object ToastUtil {

    private fun show(text: CharSequence, duration: Int) {
        Toast.makeText(BaseApplicationApp.application,text,duration).show()
    }

    fun showLong(text: CharSequence) {
        show(text,Toast.LENGTH_LONG)
    }

    fun showShort(text: CharSequence) {
        show(text,Toast.LENGTH_SHORT)
    }
}