package com.crowleysimon.remote.mapper

import com.crowleysimon.remote.model.ArticleModel
import me.toptas.rssconverter.RssItem
import javax.inject.Inject

class RssItemMapper @Inject constructor() {
    fun mapFromResponse(response: RssItem, feedUrl: String): ArticleModel {
        return ArticleModel(
            response.title,
            response.link,
            response.publishDate,
            response.description,
            response.image,
            feedUrl
        )
    }
}