package com.crowleysimon.remote.mapper

import com.crowleysimon.remote.model.ArticleModel
import com.crowleysimon.remote.model.RssItemModel
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*
import javax.inject.Inject

class RssItemMapper @Inject constructor() {
    fun mapFromResponse(response: RssItemModel, feedUrl: String, feedTitle: String?): ArticleModel {
        val formatterFeed = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        val formatterRss = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss zzz")
        val dateTime: DateTime?
        dateTime = try {
            formatterFeed.withLocale(Locale("en_US")).parseDateTime(response.pubDate)
        } catch (ignored: Exception) {
            formatterRss.withLocale(Locale("en_US")).parseDateTime(response.pubDate)
        } finally {
            DateTime.now()
        }

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
            ttl = response.ttl,
            feedTitle = feedTitle
        )
    }
}