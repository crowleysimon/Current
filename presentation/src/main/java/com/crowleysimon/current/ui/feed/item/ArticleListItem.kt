package com.crowleysimon.current.ui.feed.item

import android.view.View
import com.crowleysimon.current.R
import com.crowleysimon.current.data.ViewBindingItem
import com.crowleysimon.current.databinding.ItemArticleBinding
import com.crowleysimon.current.extensions.formatTimeStamp
import com.crowleysimon.current.extensions.loadUrl
import com.crowleysimon.domain.model.Article
import org.joda.time.DateTime
import org.jsoup.Jsoup

class ArticleListItem(
    private val guid: String,
    private val image: String,
    private val title: String,
    private val description: String,
    private val pubDate: Long?, //TODO
    private val feedTitle: String?,
    val onItemClick: (guid: String, feedId: String?) -> Unit
) : ViewBindingItem<ItemArticleBinding>(guid.hashCode().toLong()) {

    override fun inflate(itemView: View): ItemArticleBinding = ItemArticleBinding.bind(itemView)

    override fun getLayout(): Int = R.layout.item_article

    override fun bind(viewBinding: ItemArticleBinding, position: Int) {
        viewBinding.apply {
            articleImageView.loadUrl(image)
            articleTitleView.text = title
            articleSubtitleView.text = description
            pubDate?.let { millis ->
                //TODO: move this to the mapper
                articleDateView.text =
                    DateTime().withMillis(millis).formatTimeStamp(root.context)
            }
            articleCardView.setOnClickListener { onItemClick(guid, feedTitle) }
            articleFeedView.text = feedTitle
        }
    }
}

fun Article.toListItem(onItemClick: (guid: String, feedId: String?) -> Unit): ArticleListItem {
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