package com.liu.demo.wanandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.apdater.NavigationAdapter
import com.liu.demo.wanandroid.apdater.RecodingAdapter
import com.liu.demo.wanandroid.base.BaseActivity
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.IntegralBean
import com.liu.demo.wanandroid.databinding.ActivityIntegralRecodingBinding
import com.liu.demo.wanandroid.viewmodel.HomeViewModel

class IntegralRecodingActivity : BaseActivity<ActivityIntegralRecodingBinding>(ActivityIntegralRecodingBinding::inflate) {

    val model:HomeViewModel
        get() = ViewModelProvider(this).get(HomeViewModel::class.java)

    var data:MutableList<IntegralBean> = ArrayList()
    var adapter: RecodingAdapter? =null
    var isLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_integral_recoding)
        initView()
        init()
    }

    private fun initView() {
        adapter =  RecodingAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun init() {
        model.getRecoding(true)
        model.apply {
            recodingData.observe(this@IntegralRecodingActivity){
                val datas: MutableList<IntegralBean>? = it
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
            recodingMsg.observe(this@IntegralRecodingActivity){
                adapter?.loadMoreModule!!.loadMoreFail()
                isLoad = false
            }

            adapter?.loadMoreModule!!.setOnLoadMoreListener {
                if (isLoad) {
                    if (model.page < model.pageCont) {
                        model.getRecoding(false)
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