package com.crowleysimon.remote.mapper

import com.crowleysimon.remote.model.ArticleModel
import com.crowleysimon.remote.model.RssItemModel
import javax.inject.Inject
import org.joda.time.format.DateTimeFormat
import java.util.*

class RssItemMapper @Inject constructor() {
    fun mapFromResponse(response: RssItemModel, feedUrl: String): ArticleModel {
        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        val dateTime = formatter.withLocale(Locale("en_US")).parseDateTime(response.pubDate)
        return ArticleModel(
            title = response.title,
            link = response.link,
            pubDate = dateTime.millis,
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