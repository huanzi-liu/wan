package com.liu.demo.wanandroid.ui.activity

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.liu.demo.library_common.helper.GridItemDecoration
import com.liu.demo.library_common.util.ToastUtil
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.apdater.ArticleAdapter
import com.liu.demo.wanandroid.apdater.SearchAdapter
import com.liu.demo.wanandroid.base.BaseActivity
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.bean.HotKeyBean
import com.liu.demo.wanandroid.databinding.ActivitySearchBinding
import com.liu.demo.wanandroid.view.dialog.LoginDialog
import com.liu.demo.wanandroid.viewmodel.ArticleViewModel
import com.liu.demo.wanandroid.viewmodel.HomeViewModel

class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {

    var hotKeyBeans: MutableList<HotKeyBean> = ArrayList()
    var adapter: SearchAdapter? = null
    var searchBeans: MutableList<ArticleBean> = ArrayList()
    var searchAdapter: ArticleAdapter? = null
    var position =-1
    var isLoad = false
     val model: HomeViewModel
     get() = ViewModelProvider(this).get(HomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_search)
        initRecyclerView()
        init()
        initClick()
    }

    private fun init() {
        binding.hintLayout.visibility = View.VISIBLE
        binding.recyclerViewSearch.visibility = View.GONE
        model.getHotKey()
        model.apply {
            hotKeyData.observe(this@SearchActivity) {
                if (it != null) {
                    hotKeyBeans = it
                    adapter?.setList(hotKeyBeans)
                    adapter?.setOnItemClickListener { _, _, position -> getSearch(hotKeyBeans[position].name) }
                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = SearchAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP).apply {
                justifyContent = JustifyContent.FLEX_START
            }

        searchAdapter = ArticleAdapter(this)
        binding.recyclerViewSearch.adapter = searchAdapter
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearch.addItemDecoration(
            GridItemDecoration.newBuilder().horizontalDivider(
                ColorDrawable(ContextCompat.getColor(this, R.color.red)), 1, true
            ).build()
        )

    }

    var search = false
    private fun initClick() {
        binding.searchKey.setOnEditorActionListener { _, actionId, _ ->
            run {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getSearch(binding.searchKey.text.toString())
                    hideSoftInput(binding.searchKey)
                    search = true
                }
                search = false
            }
            return@setOnEditorActionListener search
        }
    }

    private fun getSearch(k:String) {
        model.getSearch(k, true)
        model.apply {
            searchData.observe(this@SearchActivity){
                val datas: MutableList<ArticleBean>? = it?.datas
                if (!datas.isNullOrEmpty()) {
                    if (model.isRefresh) {
                        searchBeans = datas
                    } else {
                        searchBeans.addAll(datas)
                    }
                    searchAdapter?.setNewInstance(searchBeans)
                    isLoad = true
                    searchAdapter?.loadMoreModule!!.loadMoreComplete()
                } else {
                    isLoad = false
                    searchAdapter?.loadMoreModule!!.loadMoreEnd(true)
                }
                binding.hintLayout.visibility = View.GONE
                binding.recyclerViewSearch.visibility = View.VISIBLE

            }
            searchMsg.observe(this@SearchActivity) {
                searchAdapter?.loadMoreModule!!.loadMoreFail()
                isLoad = false
            }

            searchAdapter?.loadMoreModule!!.setOnLoadMoreListener {
                if (isLoad) {
                    if (model.page < model.pageCont) {
                        model.getSearch(k, false)
                    } else {
                        searchAdapter?.loadMoreModule!!.loadMoreEnd(true)
                    }
                } else {
                    searchAdapter?.loadMoreModule!!.loadMoreEnd(true)
                }
            }
        }
    }

    private fun hideSoftInput(view: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}