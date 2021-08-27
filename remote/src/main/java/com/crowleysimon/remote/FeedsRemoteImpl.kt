package com.crowleysimon.remote

import com.crowleysimon.data.model.Article
import com.crowleysimon.data.repository.FeedsRemote
import com.crowleysimon.remote.mapper.ArticleModelMapper
import com.crowleysimon.remote.mapper.RssItemMapper
import com.crowleysimon.remote.service.RssService

class FeedsRemoteImpl(
    private val rssService: RssService,
    private val articleMapper: ArticleModelMapper,
    private val rssItemMapper: RssItemMapper,
) : FeedsRemote {

    override suspend fun getArticlesForFeed(feedUrl: String): List<Article> {
        val rssFeed = rssService.getArticlesForFeed(feedUrl)
        val mappedFeed = rssFeed.items.map {
            rssItemMapper.mapFromResponse(it, feedUrl, rssFeed.feedTitle)
        }
        return mappedFeed.map { articleMapper.mapFromResponse(it) }
    }

}