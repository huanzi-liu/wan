package com.liu.demo.wanandroid.apdater

import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.ALog
import com.chad.library.adapter.base.module.LoadMoreModule
import com.liu.demo.library_common.util.ToastUtil
import com.liu.demo.wanandroid.R
import com.liu.demo.wanandroid.base.BaseBindingAdapter
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.databinding.ItemArticleBinding
import com.liu.demo.wanandroid.repository.ArticleRepository
import com.liu.demo.wanandroid.ui.activity.WebViewActivity
import com.liu.demo.wanandroid.view.dialog.LoginDialog
import com.liu.demo.wanandroid.viewmodel.ArticleViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleAdapter(var activity: AppCompatActivity) :
    BaseBindingAdapter<ArticleBean, ItemArticleBinding>(ItemArticleBinding::inflate),
    LoadMoreModule {

    val rep: ArticleRepository by lazy { ArticleRepository() }
    val model:ArticleViewModel
    get() =  ViewModelProvider(activity).get(ArticleViewModel::class.java)

//    lateinit var model: ArticleViewModel
//
//    constructor(activity: AppCompatActivity, model: ArticleViewModel) : this(activity) {
//        this.model = model
//    }

    override fun convert(holder: BaseBindingHolder, item: ArticleBean) {
        holder.getViewBinding<ItemArticleBinding>().apply {
            title.text = Html.fromHtml(item.title, 1)
            author.text = if (TextUtils.isEmpty(item.author)) item.shareUser else item.author
            val tags = item.tags
            if (!tags.isNullOrEmpty()) {
                tag.visibility = View.VISIBLE
                tag.text = tags[0].name
            } else {
                tag.visibility = View.GONE
            }
            chapter.text = (item.superChapterName + "/" + item.chapterName)
            niceDate.text = item.niceDate

            if (item.fresh) {
                fresh.visibility = View.VISIBLE
            } else {
                fresh.visibility = View.GONE
            }
            if (item.collect) {
                collect.setBackgroundResource(R.mipmap.ic_collect_checked)
            } else {
                collect.setBackgroundResource(R.mipmap.ic_collect_unchecked_stroke)
            }
            title.setOnClickListener {
                WebViewActivity.startWebView(item.link)
            }
            collectLayout.setOnClickListener {
                if (item.collect) {
                    unCollect2(collect, item, getItemPosition(item))
                } else {
                    collect2(collect, item, getItemPosition(item))
                }
            }
        }
    }

    fun collect(item: ArticleBean, position: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            rep.insideCollect(item.id).apply {
                rep.responseResultRepository(this, {
                    item.collect = true
                    notifyItemChanged(position)
                    ToastUtil.showShort("收藏成功")
                }, {
                    if (TextUtils.equals(this@apply.errorCode.toString(), "-1001")) {
                        LoginDialog(context).show()
                    } else {
                        ToastUtil.showShort("收藏失败：${this@apply.errorMsg}")
                    }
                })
            }
        }
    }

    fun unCollect(item: ArticleBean, position: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            rep.insideUnCollect(item.id).apply {
                rep.responseResultRepository(this, {
                    item.collect = false
                    notifyItemChanged(position)
                    ToastUtil.showShort("取消收藏成功")
                }, {
                    if (TextUtils.equals(this@apply.errorCode.toString(), "-1001")) {
                        LoginDialog(context).show()
                    } else {
                        ToastUtil.showShort("取消收藏失败：${this@apply.errorMsg}")
                    }
                })
            }
        }
    }

    fun collect2(view: View, item: ArticleBean, position: Int) {
        model.insideCollect(item.id)
        model.apply {
            collectDataBean.removeObservers(activity)

        }.also {
            model.collectDataBean.observe(activity, {
                if (it) {
                    item.collect = true
//                notifyItemChanged(position)
//                notifyDataSetChanged()
                    view.setBackgroundResource(R.mipmap.ic_collect_checked)
                    ToastUtil.showShort("收藏成功")
                }
            })
            model.collectDataMsg.observe(activity, {
                ToastUtil.showShort("收藏失败：${it}")
            })
            model.collectDataCode.observe(activity, {
                if (TextUtils.equals("-1001", it)) {
                    val dialog = LoginDialog(context)
                    dialog.setLoginListener(object : LoginDialog.LoginListener {
                        override fun login() {
                            model.collectDataCode.value = "0"
                        }

                    })
                    dialog.show()
                }
            })
        }
    }

    fun unCollect2(view: View, item: ArticleBean, position: Int) {
        ALog.i("----Collect-----1 $position")
        model.insideUnCollect(item.id)
        model.apply {
            model.unCollectDataBean.removeObservers(activity)
        }.also {
            model.unCollectDataBean.observe(activity, {
                if (it) {
                    ALog.i("----Collect-----2 $position ${item.title}")
                    item.collect = false
//                notifyItemChanged(position)
//                notifyDataSetChanged()
                    view.setBackgroundResource(R.mipmap.ic_collect_unchecked_stroke)
                    ToastUtil.showShort("取消收藏成功")
                }
            })

            model.unCollectDataMsg.observe(activity, {
                ToastUtil.showShort("取消收藏失败：${it}")
            })
            model.unCollectDataCode.observe(activity, {
                if (TextUtils.equals("-1001", it)) {
//                LoginDialog(context).show()
                    val dialog = LoginDialog(context)
                    dialog.setLoginListener(object : LoginDialog.LoginListener {
                        override fun login() {
                            model.unCollectDataCode.value = "0"
                        }

                    })
                    dialog.show()
                }
            })
        }
    }
}