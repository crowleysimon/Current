package com.crowleysimon.remote.mapper

import com.crowleysimon.data.model.Article
import com.crowleysimon.remote.model.ArticleModel
import javax.inject.Inject

class ArticleModelMapper @Inject constructor() : ModelMapper<ArticleModel, Article> {
    override fun mapFromResponse(response: ArticleModel): Article {
        return Article(
            title = response.title,
            link = response.link,
            pubDate = response.pubDate,
            description = response.description,
            image = response.image,
            feedUrl = response.feedUrl,
            author = response.author,
            category = response.category,
            channel = response.channel,
            copyright = response.copyright,
            generator = response.generator,
            guid = response.guid,
            item = response.item,
            lastBuildDate = response.lastBuildDate,
            managingEditor = response.managingEditor,
            ttl = response.ttl,
            feedTitle = response.feedTitle,
            read = false
        )
    }
}