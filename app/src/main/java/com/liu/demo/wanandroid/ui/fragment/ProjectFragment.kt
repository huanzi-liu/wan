package com.liu.demo.wanandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankj.ALog
import com.google.android.material.tabs.TabLayoutMediator
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.base.BaseFragment
import com.liu.demo.wanandroid.bean.SystemBean
import com.liu.demo.wanandroid.databinding.FragmentProjectBinding
import com.liu.demo.wanandroid.viewmodel.ProjectViewModel
import com.liu.demo.wanandroid.viewmodel.SystemViewModel

class ProjectFragment : BaseFragment<FragmentProjectBinding>(FragmentProjectBinding::inflate) {

    var dataBean: MutableList<SystemBean> = ArrayList()

    companion object {
        @JvmStatic
        fun newInstance() = ProjectFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val model = ViewModelProvider(this).get(ProjectViewModel::class.java)
        model.getProject()
        model.dataBean.observe(viewLifecycleOwner) {
            if (it != null) {
                dataBean = it
//                adapter?.setNewInstance(dataBean)
                initViewPager(dataBean)
            }
        }
    }

    private fun initViewPager(data: MutableList<SystemBean>) {
//        binding.viewPager.adapter = object : FragmentPagerAdapter(
//            childFragmentManager,
//            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
//        ) {
//            override fun getCount(): Int = data.size
//
//            override fun getItem(position: Int): Fragment {
//                return ProjectItemFragment.newInstance(data[position].id)
//            }
//
//        }

        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = data.size

            override fun createFragment(position: Int): Fragment =
                ProjectItemFragment.newInstance(data[position].id)

        }
        binding.tabLayout.removeAllTabs()
//        binding.tabLayout.setupWithViewPager(binding.viewPager)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> run {} }.attach()


        data.forEachIndexed { index, it ->
            val tabView: View =
                LayoutInflater.from(binding.root.context).inflate(R.layout.item_tab, null)
            tabView.findViewById<AppCompatTextView>(R.id.title).text = it.name
            binding.tabLayout.getTabAt(index)?.customView = tabView
        }
        binding.tabLayout.getTabAt(0)?.select()

    }
}