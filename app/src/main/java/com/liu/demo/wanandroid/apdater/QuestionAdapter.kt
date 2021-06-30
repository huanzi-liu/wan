package com.liu.demo.wanandroid.apdater

import android.text.TextUtils
import android.view.View
import com.liu.demo.wanandroid.base.BaseBindingAdapter
import com.liu.demo.wanandroid.bean.ArticleBean
import com.liu.demo.wanandroid.databinding.ItemQuestionArticleBinding

class QuestionAdapter :BaseBindingAdapter<ArticleBean, ItemQuestionArticleBinding>(ItemQuestionArticleBinding::inflate) {
    override fun convert(holder: BaseBindingHolder, item: ArticleBean) {
        holder.getViewBinding<ItemQuestionArticleBinding>().apply {
            title.text = item.title
            author.text = if (TextUtils.isEmpty(item.author)) item.shareUser else item.author
            val tags = item.tags
            if(!tags.isNullOrEmpty()){
                tag.visibility = View.VISIBLE
                tag.text = tags[0].name
            }else{
                tag.visibility = View.GONE
            }
            chapter.text =(item.superChapterName +"/"+item.chapterName)
            niceDate.text = item.niceDate

            if (item.fresh) {
                fresh.visibility = View.VISIBLE
            }else{
                fresh.visibility = View.GONE
            }
        }
    }
}