package com.crowleysimon.current.ui.feed.model

import com.crowleysimon.domain.model.Article
import org.jsoup.Jsoup

data class ArticleListItem(
    val guid: String,
    val image: String,
    val title: String,
    val description: String,
    val pubDate: Long?, //TODO
    val onItemClick: (guid: String) -> Unit
)

fun Article.toListItem(onItemClick: (guid: String) -> Unit): ArticleListItem {
    return ArticleListItem(
        this.guid ?: "",
        this.image ?: "",
        this.title ?: "",
        Jsoup.parse(this.description).text(),
        this.pubDate,
        onItemClick
    )
}