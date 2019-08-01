package com.crowleysimon.current.ui.feed.model

import com.crowleysimon.current.R
import com.crowleysimon.current.extensions.formatTimeStamp
import com.crowleysimon.current.extensions.load
import com.crowleysimon.domain.model.Article
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_article.view.*
import org.joda.time.DateTime
import org.jsoup.Jsoup

class ArticleListItem(
    private val guid: String,
    private val image: String,
    private val title: String,
    private val description: String,
    private val pubDate: Long?, //TODO
    private val feedTitle: String?,
    val onItemClick: (guid: String) -> Unit
) : Item<ViewHolder>() {

    override fun getLayout() = R.layout.item_article

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.articleImageView.load(image)
        viewHolder.itemView.articleTitleView.text = title
        viewHolder.itemView.articleSubtitleView.text = description
        pubDate?.let { millis ->
            //TODO: move this to the mapper
            viewHolder.itemView.articleDateView.text =
                DateTime().withMillis(millis).formatTimeStamp(viewHolder.itemView.context)
        }
        viewHolder.itemView.articleCardView.setOnClickListener { onItemClick(guid) }
        viewHolder.itemView.articleFeedView.text = feedTitle
    }

}

fun Article.toListItem(onItemClick: (guid: String) -> Unit): ArticleListItem {
    return ArticleListItem(
        this.guid ?: "",
        this.image ?: "",
        this.title ?: "",
        Jsoup.parse(this.description).text(),
        this.pubDate,
        this.feedTitle,
        onItemClick
    )
}