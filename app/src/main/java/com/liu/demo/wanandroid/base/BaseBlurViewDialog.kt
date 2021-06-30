package com.liu.demo.wanandroid.base

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.blankj.ALog
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.view.dialog.BlurView

abstract class BaseBlurViewDialog<VB:ViewBinding>(context: Context, var id: Int = R.style.load_dialog) :
    BaseBindingDialog<VB>(context, id) {

    var blurView: BlurView? = null

    override fun onCreateViewBinding(layoutInflater: LayoutInflater): VB {
        return onViewBinding(layoutInflater)
    }

    override fun init() {
        val activity: Activity = getActivityFromContext(context) ?: return
        val viewGroup: ViewGroup = activity.window.decorView as ViewGroup
        blurView = viewGroup.findViewById(R.id.blur_dialog_bg)
        if (blurView == null) {
            blurView = BlurView(activity)
            blurView!!.id = R.id.blur_dialog_bg
            blurView!!.alpha = 0f
            viewGroup.addView(blurView, ViewGroup.LayoutParams(-1, -1))
        }
        initView()
    }

    private fun getActivityFromContext(context: Context): Activity? {
        var context: Context? = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (blurView != null) {
            blurView!!.blur()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (blurView != null) {
            blurView!!.show()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (blurView != null) {
            blurView!!.hide()
        }
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(listener)
        if (blurView != null) {
            blurView!!.hide()
        }
    }

    protected abstract fun onViewBinding(layoutInflater: LayoutInflater): VB
    abstract fun initView()
}