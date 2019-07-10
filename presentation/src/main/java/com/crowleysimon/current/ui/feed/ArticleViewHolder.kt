package com.crowleysimon.current.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crowleysimon.current.R
import com.crowleysimon.current.extensions.formatTimeStamp
import com.crowleysimon.current.extensions.load
import com.crowleysimon.current.ui.Bindable
import com.crowleysimon.current.ui.feed.model.ArticleListItem
import kotlinx.android.synthetic.main.item_article.view.*
import org.joda.time.DateTime

class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    Bindable<ArticleListItem> {

    override fun bind(model: ArticleListItem) {
        itemView.articleImageView.load(model.image)
        itemView.articleTitleView.text = model.title
        itemView.articleSubtitleView.text = model.description
        model.pubDate?.let { millis ->
            //TODO: move this to the mapper
            itemView.articleDateView.text =
                DateTime().withMillis(millis).formatTimeStamp(itemView.context)
        }
        itemView.articleCardView.setOnClickListener { model.onItemClick(model.guid) }
    }

    companion object {
        fun make(parent: ViewGroup) = ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }
}