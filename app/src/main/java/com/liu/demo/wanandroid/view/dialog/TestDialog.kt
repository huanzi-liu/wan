package com.liu.demo.wanandroid.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatTextView
import com.liu.demo.wanandroid.R

open class TestDialog : Dialog {

    var blurView: BlurView? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, resId: Int) : super(context, resId) {
        init(context)
    }

//    init {
//        init(context)
//    }

    fun init(context: Context) {
        setContentView(R.layout.item_article)
        var activity: Activity? = getActivityFromContext(context) ?: return
        var viewGroup: ViewGroup = activity!!.window.decorView as ViewGroup
        blurView = viewGroup.findViewById(R.id.blur_dialog_bg)
        if (blurView == null) {
            blurView = BlurView(activity)
            blurView!!.id = R.id.blur_dialog_bg
            blurView!!.alpha = 0f
            viewGroup.addView(blurView, ViewGroup.LayoutParams(-1, -1))
        }

        var window = activity.window
        var lp = activity.window.attributes
        lp.width =WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        lp.dimAmount = 0f
        window.attributes = lp
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#00000000")
        window.navigationBarColor = Color.TRANSPARENT


        findViewById<AppCompatTextView>(R.id.fresh).setOnClickListener {
            if (blurView!= null) {
                blurView!!.hide()
            }
            dismiss()
        }
//        blurView!!.setOnClickListener { dismiss() }
setCanceledOnTouchOutside(false)
    }

    fun getActivityFromContext(context: Context): Activity? {
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

}

open class BlurDialog : Dialog {
    protected var mBlurView: BlurView2? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        init(context)
    }

    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
        init(context)
    }

    private fun init(context: Context) {
        val activity = getActivityFromContext(context)
        if (activity == null) {
            Log.e("BlurDialog", "context is not a Activity Context......")
            return
        }
        setContentView(R.layout.item_article)
        val decorView = activity.window.decorView as ViewGroup
        mBlurView = decorView.findViewById(R.id.blur_dialog_bg2)
        if (mBlurView == null) {
            mBlurView = BlurView2(activity)
            mBlurView!!.id = R.id.blur_dialog_bg2
            mBlurView!!.alpha = 0f
            decorView.addView(mBlurView, ViewGroup.LayoutParams(-1, -1))
        }
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
        if (mBlurView != null) {
            mBlurView!!.blur()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mBlurView != null) {
            mBlurView!!.show()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mBlurView != null) {
            mBlurView!!.hide()
        }
    }
}