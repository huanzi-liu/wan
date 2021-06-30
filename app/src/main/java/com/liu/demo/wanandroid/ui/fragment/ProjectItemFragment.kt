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
import com.liu.demo.wanandroid.base.BaseFragment
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.databinding.FragmentProjectItemBinding
import com.liu.demo.wanandroid.ui.activity.WebViewActivity
import com.liu.demo.wanandroid.viewmodel.ProjectViewModel

class ProjectItemFragment :
    BaseFragment<FragmentProjectItemBinding>(FragmentProjectItemBinding::inflate) {
    var cid = 0
    var data: MutableList<ArticleBean> = ArrayList()
    var adapter: ArticleAdapter? = null
    var isLoad = false

    companion object {
        @JvmStatic
        fun newInstance(cid: Int): ProjectItemFragment {
            var fragment = ProjectItemFragment()
            val bundle = Bundle()
            bundle.putInt("cid", cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            cid = this.getInt("cid")
        }
        initRecyclerView()
        init()
    }

    private fun init() {
        var model = ViewModelProvider(this).get(ProjectViewModel::class.java)
        model.getProjectList(true, cid)
        model.dataBeanList.observe(viewLifecycleOwner) {
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

        model.dataMsgList.observe(viewLifecycleOwner) {
            binding.refreshLayout.isRefreshing = false
            adapter?.loadMoreModule!!.loadMoreFail()
            isLoad = false
        }

        adapter?.loadMoreModule!!.setOnLoadMoreListener {
            if (isLoad) {
                if (model.page < model.pageCont) {
                    model.getProjectList(false, cid)
                } else {
                    adapter?.loadMoreModule!!.loadMoreEnd(true)
                }
            } else {
                adapter?.loadMoreModule!!.loadMoreEnd(true)
            }
        }
        binding.refreshLayout.setOnRefreshListener {
            model.getProjectList(true, cid)
        }
    }

    private fun initRecyclerView() {
        adapter = ArticleAdapter(activity as AppCompatActivity)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        val itemDecoration = GridItemDecoration.newBuilder().horizontalDivider(
            ColorDrawable(
                ContextCompat.getColor(this.context!!, R.color.red)
            ), 1, true
        ).build()
        binding.recyclerView.addItemDecoration(itemDecoration)
    }
}