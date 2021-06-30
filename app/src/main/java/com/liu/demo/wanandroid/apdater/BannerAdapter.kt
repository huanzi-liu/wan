package com.liu.demo.wanandroid.apdater

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.liu.demo.library_common.util.GlideUtil
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.bean.BannerBean
import com.youth.banner.adapter.BannerAdapter

class BannerAdapter(context:Context,list:MutableList<BannerBean>) : BannerAdapter<BannerBean, BannerHolder>(list) {
    var context = context
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerHolder {
        return BannerHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_banner,parent,false))
    }

    override fun onBindView(holder: BannerHolder?, data: BannerBean?, position: Int, size: Int) {
        holder?.tv?.text = data?.title
        GlideUtil.loadObjectCache(context,data?.imagePath,holder?.iv!!,R.mipmap.ic_launcher)
    }
}


class BannerHolder(view: View): RecyclerView.ViewHolder(view) {
    var iv: AppCompatImageView = view.findViewById(R.id.iv)
    var tv:AppCompatTextView = view.findViewById(R.id.tv)
}
