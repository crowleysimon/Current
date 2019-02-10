package com.crowleysimon.current.ui.feed

import android.text.Html
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crowleysimon.domain.model.Article
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleViewHolder(itemView: View, private val actionListener: ActionListener?) :
    RecyclerView.ViewHolder(itemView) {


    fun bindArticle(article: Article) {
        Glide.with(itemView.context).load(article.image).into(itemView.articleImageView)
        itemView.articleTitleView.text = article.title
        /*val html = Html.fromHtml(article.description)
        itemView.articleDescriptionView.text = html.toString()
        itemView.articlePublishedDateView.text = article.pubDate*/
        itemView.articleCardView.setOnClickListener { actionListener?.onArticleClicked(article.link) }
    }

    interface ActionListener {
        fun onArticleClicked(articleUrl: String?)
    }
}