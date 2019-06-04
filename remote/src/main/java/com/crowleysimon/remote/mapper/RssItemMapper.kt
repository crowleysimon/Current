package com.crowleysimon.remote.mapper

import com.crowleysimon.remote.model.ArticleModel
import com.crowleysimon.remote.model.RssItemModel
import javax.inject.Inject

class RssItemMapper @Inject constructor() {
    fun mapFromResponse(response: RssItemModel, feedUrl: String): ArticleModel {
        return ArticleModel(
            title = response.title,
            link = response.link,
            pubDate = response.pubDate,
            description = response.description,
            image = response.image,
            feedUrl = feedUrl,
            author = response.author,
            category = response.category,
            channel = response.channel,
            copyright = response.copyright,
            generator = response.generator,
            guid = response.guid,
            item = response.item,
            lastBuildDate = response.lastBuildDate,
            managingEditor = response.managingEditor,
            ttl = response.ttl
        )
    }
}