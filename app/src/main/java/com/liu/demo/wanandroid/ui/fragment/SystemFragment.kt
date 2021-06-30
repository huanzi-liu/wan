package com.liu.demo.wanandroid.ui.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.liu.demo.library_common.helper.GridItemDecoration
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.apdater.SystemAdapter
import com.liu.demo.wanandroid.base.BaseFragment
import com.liu.demo.wanandroid.bean.SystemBean
import com.liu.demo.wanandroid.databinding.FragmentSystemBinding
import com.liu.demo.wanandroid.viewmodel.SystemViewModel

class SystemFragment : BaseFragment<FragmentSystemBinding>(FragmentSystemBinding::inflate) {
    var dataBean:MutableList<SystemBean> = ArrayList()
    var adapter :SystemAdapter? = null
    companion object {
        @JvmStatic
        fun newInstance() = SystemFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        init()
    }

    private fun init() {
        val model = ViewModelProvider(this).get(SystemViewModel::class.java)
        model.getSystem()
        model.dataBean.observe(viewLifecycleOwner){
                if (it != null) {
                    dataBean = it
                    adapter?.setNewInstance(dataBean)
                }
        }
    }

    private fun initRecyclerView() {
        adapter = SystemAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        val itemDecoration = GridItemDecoration.newBuilder().horizontalDivider(
            ColorDrawable(
                ContextCompat.getColor(this.context!!, R.color.black_alpha0)),10,false).build()
        binding.recyclerView.addItemDecoration(itemDecoration)
    }
}