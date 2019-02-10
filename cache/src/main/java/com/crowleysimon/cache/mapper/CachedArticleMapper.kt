package com.crowleysimon.cache.mapper

import com.crowleysimon.cache.model.CachedArticle
import com.crowleysimon.data.model.ArticleEntity
import javax.inject.Inject

class CachedArticleMapper @Inject constructor() : CacheMapper<CachedArticle, ArticleEntity> {

    override fun mapFromCached(cached: CachedArticle): ArticleEntity {
        return ArticleEntity(
            cached.title,
            cached.link,
            cached.pubDate,
            cached.description,
            cached.image,
            cached.read,
            cached.feedUrl
        )
    }

    override fun mapToCached(entity: ArticleEntity): CachedArticle {
        return CachedArticle(
            title = entity.title,
            link = entity.link,
            pubDate = entity.pubDate,
            description = entity.description,
            image = entity.image,
            read = entity.read ?: false,
            feedUrl = entity.feedUrl
        )
    }
}