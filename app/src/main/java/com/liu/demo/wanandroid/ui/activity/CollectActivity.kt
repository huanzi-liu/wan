package com.liu.demo.wanandroid.ui.activity

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.liu.demo.library_common.helper.GridItemDecoration
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.apdater.ArticleAdapter
import com.liu.demo.wanandroid.apdater.RecodingAdapter
import com.liu.demo.wanandroid.base.BaseActivity
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.IntegralBean
import com.liu.demo.wanandroid.databinding.ActivityCollectBinding
import com.liu.demo.wanandroid.viewmodel.ArticleViewModel
import com.liu.demo.wanandroid.viewmodel.HomeViewModel

class CollectActivity : BaseActivity<ActivityCollectBinding>(ActivityCollectBinding::inflate) {

    val model: ArticleViewModel
        get() = ViewModelProvider(this).get(ArticleViewModel::class.java)

    var data:MutableList<ArticleBean> = ArrayList()
    var adapter: ArticleAdapter? =null
    var isLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_collect)
        initView()
        init()
    }

    private fun initView() {
        adapter =  ArticleAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            GridItemDecoration.newBuilder().horizontalDivider(
                ColorDrawable(ContextCompat.getColor(this, R.color.blue)), 1, true
            ).build()
        )
    }

    private fun init() {
        model.getCollectList(true)
        model.apply {
            collectListData.observe(this@CollectActivity){
                val datas: MutableList<ArticleBean>? = it?.datas
                if (!datas.isNullOrEmpty()) {
                    if (model.isRefresh) {
                        data = datas
                    } else {
                        data.addAll(datas)
                    }
                    adapter?.setNewInstance(data)
                    isLoad = true
                    adapter?.loadMoreModule!!.loadMoreComplete()
                } else {
                    isLoad = false
                    adapter?.loadMoreModule!!.loadMoreEnd(true)
                }
            }
            collectListMsg.observe(this@CollectActivity){
                adapter?.loadMoreModule!!.loadMoreFail()
                isLoad = false
            }

            adapter?.loadMoreModule!!.setOnLoadMoreListener {
                if (isLoad) {
                    if (model.page < model.pageCont) {
                        model.getCollectList(false)
                    } else {
                        adapter?.loadMoreModule!!.loadMoreEnd(true)
                    }
                } else {
                    adapter?.loadMoreModule!!.loadMoreEnd(true)
                }
            }
        }
    }
}