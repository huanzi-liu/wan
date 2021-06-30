package com.liu.demo.wanandroid.ui.fragment

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.ALog
import com.liu.demo.library_common.helper.GridItemDecoration
import com.liu.demo.library_common.util.ToastUtil
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.apdater.ArticleAdapter
import com.liu.demo.wanandroid.apdater.BannerAdapter
import com.liu.demo.wanandroid.base.BaseFragment
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.databinding.FragmentHomeBinding
import com.liu.demo.wanandroid.http.Retrofit
import com.liu.demo.wanandroid.ui.activity.LoginActivity
import com.liu.demo.wanandroid.ui.activity.WebViewActivity
import com.liu.demo.wanandroid.view.dialog.LoginDialog
import com.liu.demo.wanandroid.viewmodel.ArticleViewModel
import com.liu.demo.wanandroid.viewmodel.HomeViewModel
import com.youth.banner.indicator.CircleIndicator

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    var data: MutableList<ArticleBean> = ArrayList()
    var adapter: ArticleAdapter? = null
    var position =-1

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_home)
////        initRecyclerView()
//        init()
//    }
    var isLoad = false
        val model2 :ArticleViewModel
        get()= ViewModelProvider(this).get(ArticleViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        init()
    }

    fun init() {
        binding.banner.addBannerLifecycleObserver(this@HomeFragment)
        binding.banner.setIndicator(CircleIndicator(this@HomeFragment.context))
        val model = ViewModelProvider(this).get(HomeViewModel::class.java)
        model.getBanner()
        model.getArticle(true)
        model.apply {
            bannerData.observe(viewLifecycleOwner) {
                Log.i("TAG0", it.toString())
                binding.banner.setAdapter(it?.let { it1 ->
                    BannerAdapter(
                        this@HomeFragment.activity!!,
                        it1
                    )
                })
            }
            articleDataBean.observe(viewLifecycleOwner) {
                var datas: MutableList<ArticleBean>? = it?.datas
                if (!datas.isNullOrEmpty()) {
                    if (model.isRefresh) {
                        data = datas
                    } else {
                        data.addAll(datas)
                    }
                    adapter?.setNewInstance(data)
//                    adapter?.setOnItemClickListener { _, _, position ->
//                        WebViewActivity.startWebView(data[position].link)
//                    }
                    adapter?.setOnItemChildClickListener { _, view, position ->
                        ALog.i("====--====$position")
                        when(view.id){
//                            R.id.title ->WebViewActivity.startWebView(data[position].link)
//                            R.id.collectLayout -> {
//                                when {
//                                    data[position].collect -> {
////                                        model2.insideUnCollect(data[position].id).also {
////                                            this@HomeFragment.position = position
////                                        }
//                                        adapter?.unCollect(data[position],position)
//                                    }
//                                    else -> {
////                                        model2.insideCollect(data[position].id).also {
////                                            this@HomeFragment.position = position
////                                        }
//                                        adapter?.collect(data[position],position)
//                                    }
//                                }
//                            }
                        }
                    }
                    isLoad = true
                    adapter?.loadMoreModule!!.loadMoreComplete()
                } else {
                    isLoad = false
                    adapter?.loadMoreModule!!.loadMoreEnd(true)
                }
                binding.refreshLayout.isRefreshing = false
//                adapter?.loadMoreModule!!.loadMoreEnd(true)

            }

            articleDataMsg.observe(viewLifecycleOwner) {
                binding.refreshLayout.isRefreshing = false
                adapter?.loadMoreModule!!.loadMoreFail()
                isLoad = false
            }

            adapter?.loadMoreModule!!.setOnLoadMoreListener {
                if (isLoad) {
                    if (model.page < model.pageCont) {
                        model.getArticle(false)
                    } else {
                        adapter?.loadMoreModule!!.loadMoreEnd(true)
                    }
                } else {
                    adapter?.loadMoreModule!!.loadMoreEnd(true)
                }
            }
        }

//        model2.collectDataBean.observe(viewLifecycleOwner){
//            data[position].collect = true
//            adapter?.notifyItemChanged(position)
//            ToastUtil.showShort("收藏成功")
//        }
//        model2.collectDataMsg.observe(viewLifecycleOwner){
//            ToastUtil.showShort("收藏失败：${it}")
//        }
//        model2.collectDataCode.observe(viewLifecycleOwner){
//            if (TextUtils.equals(it,"-1001")){
//                LoginDialog(context!!).show()
//            }
//        }
//        model2.unCollectDataBean.observe(viewLifecycleOwner){
//            data[position].collect = false
//            adapter?.notifyItemChanged(position)
//            ToastUtil.showShort("取消收藏成功")
//        }
//        model2.unCollectDataMsg.observe(viewLifecycleOwner){
//            ToastUtil.showShort("取消收藏失败：${it}")
//        }
//        model2.unCollectDataCode.observe(viewLifecycleOwner){
//            if (TextUtils.equals(it,"-1001")){
//                LoginDialog(context!!).show()
//            }
//        }
//        adapter?.loadMoreModule?.isAutoLoadMore = false
        binding.refreshLayout.setOnRefreshListener {
            model.getArticle(true)
        }
    }

    private fun initRecyclerView() {
        adapter = ArticleAdapter(activity as AppCompatActivity)
        adapter!!.loadMoreModule.preLoadNumber = 0
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        val itemDecoration = GridItemDecoration.newBuilder().horizontalDivider(
            ColorDrawable(ContextCompat.getColor(this.context!!, R.color.red)),
            1,
            true
        ).build()
        binding.recyclerView.addItemDecoration(itemDecoration)
    }


//    override fun initBinding(view: View): FragmentHomeBinding = FragmentHomeBinding.bind(view)
}