package com.liu.demo.wanandroid.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseBindingDialog<VB : ViewBinding> : Dialog {

    lateinit var binding: VB

    constructor(context: Context) : this(context, 0)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = onCreateViewBinding(layoutInflater)
        setContentView(binding.root)
        init()
    }

    protected abstract fun onCreateViewBinding(layoutInflater: LayoutInflater): VB
    abstract fun init()
}