package com.liu.demo.wanandroid.view.dialog

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.liu.demo.wanandroid.base.BaseBlurViewDialog
import com.liu.demo.wanandroid.bean.UserInfo
import com.liu.demo.wanandroid.databinding.DialogIntegralBinding
import com.liu.demo.wanandroid.ui.activity.IntegralRecodingActivity

class IntegralDialog(context: Context, val data: UserInfo) :
    BaseBlurViewDialog<DialogIntegralBinding>(context) {
    override fun onViewBinding(layoutInflater: LayoutInflater)=DialogIntegralBinding.inflate(layoutInflater)

    override fun initView() {
        binding.tvIntegral.text = data.coinCount.toString()
        binding.tvLevel.text = data.level.toString()
        binding.tvRank.text = data.rank.toString()

        binding.tvRecording.setOnClickListener {
            context.startActivity(Intent(context,IntegralRecodingActivity::class.java))
            dismiss()
        }
    }

}