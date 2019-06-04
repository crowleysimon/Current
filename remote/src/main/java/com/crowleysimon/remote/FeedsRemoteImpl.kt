package com.crowleysimon.remote

import com.crowleysimon.data.model.ArticleEntity
import com.crowleysimon.data.repository.FeedsRemote
import com.crowleysimon.remote.mapper.ArticleModelMapper
import com.crowleysimon.remote.mapper.RssItemMapper
import com.crowleysimon.remote.model.RssFeedModel
import com.crowleysimon.remote.service.RssService
import io.reactivex.Single
import javax.inject.Inject

class FeedsRemoteImpl @Inject constructor(
    private val rssService: RssService,
    private val articleMapper: ArticleModelMapper,
    private val rssItemMapper: RssItemMapper
) : FeedsRemote {
    override fun getArticlesForFeed(feedUrl: String): Single<List<ArticleEntity>> {
        return rssService.getArticlesForFeed(feedUrl)
            .map { rssFeed: RssFeedModel ->
                rssFeed.items.map { rssItemMapper.mapFromResponse(it, feedUrl) }
            }
            .map { articleList ->
                articleList.map { articleMapper.mapFromResponse(it) }
            }
    }

}