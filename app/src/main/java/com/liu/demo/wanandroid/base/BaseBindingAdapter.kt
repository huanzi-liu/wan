package com.liu.demo.wanandroid.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

//abstract class BaseBindingAdapter<T, VB : ViewBinding>(
//    val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
//    layoutResId: Int =-1,
//    data: MutableList<T>?
//) :
//    BaseQuickAdapter<T, BaseBindingAdapter.BaseBindingHolder>(layoutResId, data) {
//
//    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int) =
//        BaseBindingHolder(inflate(LayoutInflater.from(parent.context), parent, false))
//
//    class BaseBindingHolder(private val viewBinding: ViewBinding) :
//        BaseViewHolder(viewBinding.root) {
//        constructor(view: View) : this(ViewBinding { view })
//
//        fun <VB : ViewBinding> getViewBinding() = viewBinding as VB
//    }
//
//}

abstract class BaseBindingAdapter<T, VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
    layoutResId: Int = -1,
) :
    BaseQuickAdapter<T, BaseBindingAdapter.BaseBindingHolder>(layoutResId) {

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int) =
        BaseBindingHolder(inflate(LayoutInflater.from(parent.context), parent, false))

    class BaseBindingHolder(private val binding: ViewBinding) : BaseViewHolder(binding.root) {
        constructor(itemView: View) : this(ViewBinding { itemView })

        @Suppress("UNCHECKED_CAST")
        fun <VB : ViewBinding> getViewBinding() = binding as VB
    }
}