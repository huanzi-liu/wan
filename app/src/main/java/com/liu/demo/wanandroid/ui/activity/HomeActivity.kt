package com.liu.demo.wanandroid.ui.activity

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.*
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankj.ALog
import com.liu.demo.library_common.util.TimerUtil
import com.liu.demo.library_common.util.ToastUtil
import com.liu.demo.wanandroid.apdater.FragmentAdapter
import com.liu.demo.wanandroid.base.BaseActivity
import com.liu.demo.wanandroid.bean.HotKeyBean
import com.liu.demo.wanandroid.constant.Constant
import com.liu.demo.wanandroid.databinding.ActivityHomeBinding
import com.liu.demo.wanandroid.ui.fragment.*
import com.liu.demo.wanandroid.view.dialog.IntegralDialog
import com.liu.demo.wanandroid.view.dialog.LoginDialog
import com.liu.demo.wanandroid.viewmodel.HomeViewModel
import java.util.concurrent.TimeUnit


class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private val model: HomeViewModel
        get() = ViewModelProvider(this).get(HomeViewModel::class.java)

    var datas: MutableList<HotKeyBean> = ArrayList()
    private val fragments = arrayListOf(
        HomeFragment.newInstance(),
        NavigationFragment.newInstance(),
        QuestionFragment.newInstance(),
        SystemFragment.newInstance(),
        ProjectFragment.newInstance()
    )
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//        init()
        initClick()
        initViewPager()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun init() {
//        val model = ViewModelProvider(this).get(HomeViewModel::class.java)
        model.getHotKey()
        model.apply {
            hotKeyData.observe(this@HomeActivity) {
                if (it != null) {
                    datas = it
                    if (index > datas.size - 1) {
                        index = 0
                    }
                    binding.hotKey.text = datas[index].name
                    TimerUtil.intervalTimer(
                        2000,
                        TimeUnit.MILLISECONDS,
                        "SSS",
                        object : TimerUtil.INext {
                            override fun doNext(number: Long) {
                                index++
                                if (index > datas.size - 1) {
                                    index = 0
                                }
                                binding.hotKey.text = datas[index].name
                            }
                        })
                }
            }
            hotKeyMsg.observe(this@HomeActivity) {
                it?.let { it1 -> ToastUtil.showShort(it1) }
            }

        }
        binding.hotKey.movementMethod = ScrollingMovementMethod()

    }

    private fun initClick() {
        binding.home.setOnClickListener {
            binding.viewPager.currentItem = 0
        }
        binding.navigation.setOnClickListener {
            binding.viewPager.currentItem = 1
        }
        binding.question.setOnClickListener {
            binding.viewPager.currentItem = 2
        }
        binding.system.setOnClickListener {
            binding.viewPager.currentItem = 3
        }
        binding.project.setOnClickListener {
            binding.viewPager.currentItem = 4
        }
        binding.hotKey.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        binding.menu.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        binding.tvLogin.setOnClickListener {
            if (TextUtils.equals(binding.tvLogin.text.toString(), "去登录")) {
                isShowLogin(Constant.loginCode)
            }
        }
        binding.tvIntegral.setOnClickListener { getIntegral() }

        binding.tvCollect.setOnClickListener {
            if (TextUtils.equals(binding.tvLogin.text.toString(), "去登录")) {
                isShowLogin(Constant.loginCode)
            }else{
                startActivity(Intent(this,CollectActivity::class.java))
            }
        }
        binding.tvSetting.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }

    private fun initViewPager() {
        binding.viewPager.adapter = FragmentAdapter(supportFragmentManager, fragments)
//        binding.viewPager.adapter =object: FragmentStateAdapter(supportFragmentManager){
//            override fun getItemCount(): Int = fragments.size
//
//            override fun createFragment(position: Int): Fragment = fragments[position]
//        }
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.currentItem = 0

    }

    private fun getIntegral() {
        model.getIntegral()
        model.apply {
            userInfoData.observe(this@HomeActivity) {
                binding.tvLogin.text = it?.username
                if (it != null) {
                    val dialog = IntegralDialog(this@HomeActivity, it)
                    if (dialog.isShowing) {
                        dialog.dismiss()
                    } else {
                        dialog.show()
                    }
                }
            }
            userInfoCode.observe(this@HomeActivity) {
                isShowLogin(it)
            }
            userInfoMsg.observe(this@HomeActivity) {
                it?.let { it1 -> ToastUtil.showShort(it1) }
            }
        }
    }

    private fun get() {

    }

    private fun isShowLogin(code: Int) {
        if (code == Constant.loginCode) {
            val loginDialog: LoginDialog = LoginDialog(this@HomeActivity)
            loginDialog.setLoginListener(object : LoginDialog.LoginListener {
                override fun login() {
                    binding.tvLogin.text = "---"
                }
            })
            loginDialog.show()
        }
    }

    override fun onStop() {
        super.onStop()
        TimerUtil.cancel("SSS")
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }
}