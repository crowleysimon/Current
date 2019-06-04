package com.crowleysimon.remote

import com.crowleysimon.data.model.ArticleEntity
import com.crowleysimon.data.repository.FeedsRemote
import com.crowleysimon.remote.mapper.ArticleModelMapper
import com.crowleysimon.remote.mapper.RssItemMapper
import com.crowleysimon.remote.service.RssService
import io.reactivex.Single
import me.toptas.rssconverter.RssFeed
import javax.inject.Inject

class FeedsRemoteImpl @Inject constructor(
    private val rssService: RssService,
    private val articleMapper: ArticleModelMapper,
    private val rssItemMapper: RssItemMapper
) : FeedsRemote {
    override fun getArticlesForFeed(feedUrl: String): Single<List<ArticleEntity>> {
        return rssService.getArticlesForFeed(feedUrl)
            .map { rssFeed: RssFeed ->
                rssFeed.items?.map { rssItemMapper.mapFromResponse(it, feedUrl) } // fix ? operator
            }
            .map { articleList ->
                articleList.map { articleMapper.mapFromResponse(it) }
            }
    }

}