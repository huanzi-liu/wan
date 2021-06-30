package com.liu.demo.wanandroid.ui.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.ALog
import com.liu.demo.library_common.helper.GridItemDecoration
import com.liu.demo.library_common.util.ToastUtil
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.apdater.ArticleAdapter
import com.liu.demo.wanandroid.base.BaseActivity
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.databinding.ActivityArticleBinding
import com.liu.demo.wanandroid.viewmodel.ArticleViewModel

class ArticleActivity : BaseActivity<ActivityArticleBinding>(ActivityArticleBinding::inflate) {

    var data: MutableList<ArticleBean> = ArrayList()
    var adapter: ArticleAdapter? = null
    var cid = 0
    var isLoad = false

//    companion object {
//        fun newInstance(cid: Int) {
//            var activity = ArticleActivity()
//            val intent = Intent(BaseApplication.application, ArticleActivity::class.java)
////            val bundle = Bundle()
////            bundle.putInt("cid", cid)
////            intent.putExtras(bundle)
//            intent.putExtra("cid", cid)
//            BaseApplication.application.startActivity(intent)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_article)

//        cid = intent.extras?.get("cid") as Int
        cid = intent.getIntExtra("cid", 0)

        initRecyclerView()
        init()
    }

        val model:ArticleViewModel
    get() = ViewModelProvider(this).get(ArticleViewModel::class.java)
    private fun init() {
        model.getArticleList(true, cid)
        model.articleDataBean.observe(this, Observer {
            var datas: MutableList<ArticleBean>? = it?.datas
            if (!datas.isNullOrEmpty()) {
                if (model.isRefresh) {
                    data = datas
                } else {
                    data.addAll(datas)
                }
                adapter?.setList(data)
//                adapter?.setOnItemClickListener { _, _, position ->
//                    WebViewActivity.startWebView(data[position].link)
//                }
//                adapter?.setOnItemChildClickListener { _, view, position ->
//                    when(view.id){
//                        R.id.title ->WebViewActivity.startWebView(data[position].link)
//                        R.id.collectLayout -> model.insideCollect(data[position].id)
//                    }
//
//                }
                isLoad = true
                adapter?.loadMoreModule!!.loadMoreComplete()
            } else {
                isLoad = false
                adapter?.loadMoreModule!!.loadMoreEnd(true)
            }
            binding.refreshLayout.isRefreshing = false

        })

        model.collectDataBean.observe(this){
            ToastUtil.showShort("收藏成功")
        }
        model.collectDataMsg.observe(this){
            ToastUtil.showShort("收藏失败：${it}")
        }
        model.articleDataMsg.observe(this) {
            binding.refreshLayout.isRefreshing = false
            adapter?.loadMoreModule!!.loadMoreFail()
            isLoad = false
        }

        adapter?.loadMoreModule!!.setOnLoadMoreListener {
            if (isLoad) {
                if (model.page < model.pageCont) {
                    model.getArticleList(false,cid)
                } else {
                    adapter?.loadMoreModule!!.loadMoreEnd(true)
                }
            } else {
                adapter?.loadMoreModule!!.loadMoreEnd(true)
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            model.getArticleList(true,cid)
        }
    }

    private fun initRecyclerView() {
        adapter = ArticleAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        val itemDecoration = GridItemDecoration.newBuilder().horizontalDivider(
            ColorDrawable(
                ContextCompat.getColor(this, R.color.red)
            ), 1, true
        ).build()
        binding.recyclerView.addItemDecoration(itemDecoration)
        ALog.i("TTT")
    }
}