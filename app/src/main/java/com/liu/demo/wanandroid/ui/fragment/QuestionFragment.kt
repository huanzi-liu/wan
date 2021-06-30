package com.liu.demo.wanandroid.ui.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.liu.demo.library_common.helper.GridItemDecoration
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.apdater.ArticleAdapter
import com.liu.demo.wanandroid.apdater.QuestionAdapter
import com.liu.demo.wanandroid.base.BaseFragment
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.databinding.FragmentQuestionBinding
import com.liu.demo.wanandroid.ui.activity.WebViewActivity
import com.liu.demo.wanandroid.viewmodel.QuestionViewModel

class QuestionFragment:BaseFragment<FragmentQuestionBinding>(FragmentQuestionBinding::inflate) {
    var data:MutableList<ArticleBean> = ArrayList()
//    var adapter :QuestionAdapter? = null
    var adapter :ArticleAdapter? = null
    var isLoad = false
    companion object{
        @JvmStatic
        fun newInstance() = QuestionFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        init()
    }

    private fun init() {
        val model = ViewModelProvider(this).get(QuestionViewModel::class.java)
        model.getQuestion(true)
        model.dataBean.observe(viewLifecycleOwner){
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
                isLoad = true
            adapter?.loadMoreModule!!.loadMoreComplete()
            } else {
                isLoad = false
                adapter?.loadMoreModule!!.loadMoreEnd(true)
            }
            binding.refreshLayout.isRefreshing = false

        }

        model.dataMsg.observe(viewLifecycleOwner) {
            binding.refreshLayout.isRefreshing = false
            adapter?.loadMoreModule!!.loadMoreFail()
            isLoad = false
        }

        adapter?.loadMoreModule!!.setOnLoadMoreListener {
            if (isLoad) {
                if (model.page < model.pageCont) {
                    model.getQuestion(false)
                } else {
                    adapter?.loadMoreModule!!.loadMoreEnd(true)
                }
            } else {
                adapter?.loadMoreModule!!.loadMoreEnd(true)
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            model.getQuestion(true)
        }
    }

    private fun initRecyclerView() {
//        adapter = QuestionAdapter()
        adapter = ArticleAdapter(activity as AppCompatActivity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        val itemDecoration = GridItemDecoration.newBuilder().horizontalDivider(ColorDrawable(ContextCompat.getColor(this.context!!,R.color.blue)),1,false).build()
        binding.recyclerView.addItemDecoration(itemDecoration)
    }
}