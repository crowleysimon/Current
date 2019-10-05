package com.crowleysimon.current.ui.feed.model

import com.crowleysimon.current.R
import com.crowleysimon.current.extensions.formatTimeStamp
import com.crowleysimon.current.extensions.load
import com.crowleysimon.domain.model.Article
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_article.*
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
) : Item() {
    override fun getLayout(): Int = R.layout.item_article

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            articleImageView.load(image)
            articleTitleView.text = title
            articleSubtitleView.text = description
            pubDate?.let { millis ->
                //TODO: move this to the mapper
                itemView.articleDateView.text =
                    DateTime().withMillis(millis).formatTimeStamp(itemView.context)
            }
            articleCardView.setOnClickListener { onItemClick(guid) }
            articleFeedView.text = feedTitle
        }
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