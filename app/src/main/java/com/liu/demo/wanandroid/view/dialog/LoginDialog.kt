package com.liu.demo.wanandroid.view.dialog

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.ALog
import com.liu.demo.library_common.util.ToastUtil
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.base.BaseBindingDialog
import com.liu.demo.wanandroid.databinding.DialogLoginBinding
import com.liu.demo.wanandroid.repository.LoginRegisterRepository
import kotlinx.coroutines.runBlocking

class LoginDialog(context: Context, var id: Int = R.style.load_dialog) :
    BaseBindingDialog<DialogLoginBinding>(context, id) {

    var blurView: BlurView? = null

    override fun onCreateViewBinding(layoutInflater: LayoutInflater): DialogLoginBinding {
        return DialogLoginBinding.inflate(layoutInflater)
    }

    override fun init() {
        val activity: Activity = getActivityFromContext(context) ?: return
        val viewGroup: ViewGroup = activity.window.decorView as ViewGroup
        blurView = null
        blurView = viewGroup.findViewById(R.id.blur_dialog_bg)
        if (blurView == null) {
            blurView = BlurView(activity)
            blurView!!.id = R.id.blur_dialog_bg
            blurView!!.alpha = 0f
            viewGroup.addView(blurView, ViewGroup.LayoutParams(-1, -1))
        }
//        binding.name.text = "name"
//        binding.password.text="password"

//        val window = activity.window
//        val lp = activity.window.attributes
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT
//        lp.dimAmount = 0f
//        window.attributes = lp
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = Color.parseColor("#00000000")
//        window.navigationBarColor = Color.TRANSPARENT
        click(activity)
    }

    private fun click(activity: Activity) {

//        var model = LoginRegisterViewModel(BaseApplication.application)
//        model.login(name,password).apply {
//            model.loginData.observe()
//        }
        val rep: LoginRegisterRepository = LoginRegisterRepository()

        binding.login.setOnClickListener {
            val name: String = binding.name.text.toString()
            val password: String = binding.password.text.toString()
            runBlocking {
                rep.login(name, password).apply {
                    rep.responseResultRepository(this, {
                        ALog.i("登录成功${this@apply.data}")
                        if (blurView != null) {
                            blurView!!.hide()
                        }
                        dismiss()
                        listener?.login()
                    }, {
                        ToastUtil.showShort("登录失败:${this@apply.errorMsg}")
                        ALog.i("登录失败${this@apply.errorMsg}")
                    })
                }
            }
        }

    }

    private var listener: LoginListener?= null
    fun setLoginListener(loginListener: LoginListener) {
        this.listener = loginListener
    }

    interface LoginListener {
        fun login()
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
}
