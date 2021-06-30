package com.liu.demo.wanandroid.apdater

import com.liu.demo.wanandroid.base.BaseBindingAdapter
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.NavigationBean
import com.liu.demo.wanandroid.databinding.ItemItemNavigationBinding
import com.liu.demo.wanandroid.databinding.ItemNavigationBinding

class NavigationAdapter :
    BaseBindingAdapter<NavigationBean, ItemNavigationBinding>(ItemNavigationBinding::inflate) {
    override fun convert(holder: BaseBindingHolder, item: NavigationBean) {
        holder.getViewBinding<ItemNavigationBinding>().apply {
            name.text = item.name
        }
    }
}

class NavigationItemAdapter :
    BaseBindingAdapter<ArticleBean, ItemItemNavigationBinding>(ItemItemNavigationBinding::inflate) {
    override fun convert(holder: BaseBindingHolder, item: ArticleBean) {
        holder.getViewBinding<ItemItemNavigationBinding>().apply {
            name.text = item.title
        }
    }

}