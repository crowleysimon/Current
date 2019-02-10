package com.crowleysimon.current.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crowleysimon.current.R
import com.crowleysimon.domain.model.Article
import javax.inject.Inject

class FeedAdapter @Inject constructor() : RecyclerView.Adapter<ArticleViewHolder>() {

    var data: List<Article> = arrayListOf()
    var actionListener: ArticleViewHolder.ActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(itemView, actionListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = data[position]
        holder.bindArticle(article)
    }
}