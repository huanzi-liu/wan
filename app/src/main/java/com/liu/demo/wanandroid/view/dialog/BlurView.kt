package com.liu.demo.wanandroid.view.dialog

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.widget.AppCompatImageView

class BlurView(context: Context) : AppCompatImageView(context) {
    var tag = 0

    fun show() {
        if (tag++ <= 0) {
            animate().alpha(1f).setDuration(300).start()
        }
    }

    fun hide() {
        if (--tag <= 0) {
            animate().alpha(0f).setDuration(300).start()
        }
    }

    fun blur() {
        var activity: Activity = context as Activity
        if (tag <= 0) {
            var view = activity.window.decorView
            var bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            var canvas = Canvas(bitmap)
            view.draw(canvas)
            background = BitmapDrawable(resources, BlurUtils.blur(activity, bitmap, 4f, 1f))
            tag = 0
        }
    }
}

class BlurView2(context: Context?) : AppCompatImageView(
    context!!
) {
    private var tag = 0
    fun show() {
        if (tag++ <= 0) {
            animate().alpha(1f).setDuration(300).start()
        }
    }

    fun hide() {
        if (--tag <= 0) {
            animate().alpha(0f).setDuration(300).start()
        }
    }

    fun blur() {
        val activity = context as Activity
        if (tag <= 0) {
            val decorView1 = activity.window.decorView
            val bitmap =
                Bitmap.createBitmap(decorView1.width, decorView1.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            decorView1.draw(canvas)
            background = BitmapDrawable(resources, BlurUtils2.blur(activity, bitmap, 4, 0.2f))
            tag = 0
        }
    }
}