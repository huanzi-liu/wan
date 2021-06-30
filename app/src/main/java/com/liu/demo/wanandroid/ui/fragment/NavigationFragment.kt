package com.liu.demo.wanandroid.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.liu.demo.wanandroid.apdater.NavigationAdapter
import com.liu.demo.wanandroid.apdater.NavigationItemAdapter
import com.liu.demo.wanandroid.base.BaseFragment
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.NavigationBean
import com.liu.demo.wanandroid.databinding.FragmentNavigationBinding
import com.liu.demo.wanandroid.ui.activity.WebViewActivity
import com.liu.demo.wanandroid.viewmodel.NavigationViewModel

class NavigationFragment :
    BaseFragment<FragmentNavigationBinding>(FragmentNavigationBinding::inflate) {

    var data: MutableList<NavigationBean> = ArrayList()
    var itemData: MutableList<ArticleBean> = ArrayList()
    var adapter: NavigationAdapter? = null
    var itemAdapter: NavigationItemAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance() = NavigationFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        init()
    }

    private fun init() {
        val model = ViewModelProvider(this).get(NavigationViewModel::class.java)
        model.getNavigation()
        model.apply {
            dataBean.observe(viewLifecycleOwner) {
                it?.let { it1 ->
                    adapter?.setNewInstance(it1)
                    itemData = it1[0].articles
                    itemAdapter?.setNewInstance(itemData)
                }

                adapter?.setOnItemClickListener { a, view, position ->
                    itemData = it?.get(position)?.articles!!
                    itemAdapter?.setNewInstance(itemData)
                }
                if (!itemData.isNullOrEmpty()){
                    itemAdapter?.setOnItemClickListener { adapter, view, position ->
                        WebViewActivity.startWebView(itemData[position].link)
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = NavigationAdapter()
        binding.recyclerViewNavigation.adapter = adapter
        binding.recyclerViewNavigation.layoutManager = LinearLayoutManager(activity)

        itemAdapter = NavigationItemAdapter()
        binding.label.adapter = itemAdapter
        val layoutManager = FlexboxLayoutManager(activity, FlexDirection.ROW, FlexWrap.WRAP)
        layoutManager.justifyContent = JustifyContent.FLEX_START

        binding.label.layoutManager = layoutManager
        binding.label.overScrollMode = View.OVER_SCROLL_NEVER
    }
}