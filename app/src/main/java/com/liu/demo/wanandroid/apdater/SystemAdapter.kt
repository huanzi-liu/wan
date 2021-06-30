package com.liu.demo.wanandroid.apdater

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.liu.demo.wanandroid.base.BaseBindingAdapter
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.SystemBean
import com.liu.demo.wanandroid.databinding.ItemItemNavigationBinding
import com.liu.demo.wanandroid.databinding.ItemSystemBinding
import com.liu.demo.wanandroid.ui.activity.ArticleActivity

class SystemAdapter :BaseBindingAdapter<SystemBean, ItemSystemBinding>(ItemSystemBinding::inflate) {
    override fun convert(holder: BaseBindingHolder, item: SystemBean) {
        holder.getViewBinding<ItemSystemBinding>().apply {
            name.text = item.name
            var adapter = SystemItemAdapter()
            recyclerView.adapter = adapter
            recyclerView.layoutManager = FlexboxLayoutManager(context,FlexDirection.ROW,FlexWrap.WRAP)
            adapter.setNewInstance(item.children)

            adapter.setOnItemClickListener { adapter, view, position ->
                item.children?.get(position)?.let {
                    context.startActivity(
                        Intent(
                            context,
                            ArticleActivity::class.java
                        ).putExtra("cid", it.id)
                    )
                }
            }
        }
    }
}

class SystemItemAdapter :
    BaseBindingAdapter<SystemBean, ItemItemNavigationBinding>(ItemItemNavigationBinding::inflate) {
    override fun convert(holder: BaseBindingHolder, item: SystemBean) {
        holder.getViewBinding<ItemItemNavigationBinding>().apply {
            name.text = item.name
        }
    }

}