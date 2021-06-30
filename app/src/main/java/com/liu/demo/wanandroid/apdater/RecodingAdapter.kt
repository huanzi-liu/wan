package com.liu.demo.wanandroid.apdater

import android.view.Gravity
import android.view.WindowManager
import com.chad.library.adapter.base.module.LoadMoreModule
import com.liu.demo.wanandroid.base.BaseBindingAdapter
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.IntegralBean
import com.liu.demo.wanandroid.bean.NavigationBean
import com.liu.demo.wanandroid.databinding.ItemItemNavigationBinding
import com.liu.demo.wanandroid.databinding.ItemNavigationBinding

class RecodingAdapter :
    BaseBindingAdapter<IntegralBean, ItemNavigationBinding>(ItemNavigationBinding::inflate),
    LoadMoreModule {
    override fun convert(holder: BaseBindingHolder, item: IntegralBean) {
        holder.getViewBinding<ItemNavigationBinding>().apply {
            name.text = item.desc
            name.gravity = Gravity.START
        }
    }
}
