package com.liu.demo.wanandroid.apdater

import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.base.BaseBindingAdapter
import com.liu.demo.wanandroid.bean.HotKeyBean
import com.liu.demo.wanandroid.databinding.ItemTitleBinding

class SearchAdapter :BaseBindingAdapter<HotKeyBean,ItemTitleBinding>(ItemTitleBinding::inflate) {
    override fun convert(holder: BaseBindingHolder, item: HotKeyBean) {
        holder.getViewBinding<ItemTitleBinding>().apply {
            title.text = item.name
            addChildClickViewIds(R.id.title)
        }
    }
}