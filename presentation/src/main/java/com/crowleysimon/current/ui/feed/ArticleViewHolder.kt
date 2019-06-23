package com.crowleysimon.current.ui.feed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crowleysimon.current.R
import com.crowleysimon.current.extensions.formatTimeStamp
import com.crowleysimon.domain.model.Article
import kotlinx.android.synthetic.main.item_article.view.*
import org.joda.time.DateTime
import org.jsoup.Jsoup

class ArticleViewHolder(itemView: View, private val actionListener: ActionListener?) :
    RecyclerView.ViewHolder(itemView) {

    fun bindArticle(article: Article) {
        itemView.articleImageView.load(article.image)
        itemView.articleTitleView.text = article.title
        itemView.articleSubtitleView.text = Jsoup.parse(article.description).text()
        article.pubDate?.let { millis ->
            //TODO: move this to the mapper in the data layer?
            itemView.articleDateView.text = DateTime().withMillis(millis).formatTimeStamp(itemView.context)
        }
        itemView.articleCardView.setOnClickListener { actionListener?.onArticleClicked(article.link) }
    }

    interface ActionListener {
        fun onArticleClicked(articleUrl: String?)
    }
}