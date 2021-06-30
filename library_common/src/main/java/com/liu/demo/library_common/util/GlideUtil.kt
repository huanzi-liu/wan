package com.liu.demo.library_common.util

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object GlideUtil {
    fun loadObject(context: Context, obj: Any?, imageView: AppCompatImageView) {
        Glide.with(context).load(obj).into(imageView)
    }

    fun loadObjectNoCache(
        context: Context,
        obj: Any?,
        imageView: AppCompatImageView,
        drawable: Drawable
    ) {
        val options =
            RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(drawable).placeholder(drawable)
        if (context is Activity && !context.isDestroyed) {
            Glide.with(context).load(obj).apply(options).into(imageView)
        }
    }
    fun loadObjectNoCache(
        context: Context,
        obj: Any?,
        imageView: AppCompatImageView,
        @DrawableRes
        drawable: Int
    ) {
        val options =
            RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(drawable).placeholder(drawable)
        if (context is Activity && !context.isDestroyed) {
            Glide.with(context).load(obj).apply(options).into(imageView)
        }
    }

    fun loadObjectCache(context: Context,
                        obj: Any?,
                        imageView: AppCompatImageView,
                        drawable: Drawable) {
        val options = RequestOptions().error(drawable).placeholder(drawable)
        if (context is Activity && !context.isDestroyed) {
            Glide.with(context).load(obj).apply(options).into(imageView)
        }
    }
    fun loadObjectCache(context: Context,
                        obj: Any?,
                        imageView: AppCompatImageView,
                        @DrawableRes
                        drawable: Int) {
        val options = RequestOptions().error(drawable).placeholder(drawable)
        if (context is Activity && !context.isDestroyed) {
            Glide.with(context).load(obj).apply(options).into(imageView)
        }
    }
}