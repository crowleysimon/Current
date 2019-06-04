package com.crowleysimon.cache.mapper

import com.crowleysimon.cache.model.CachedArticle
import com.crowleysimon.data.model.ArticleEntity
import javax.inject.Inject

class CachedArticleMapper @Inject constructor() : CacheMapper<CachedArticle, ArticleEntity> {

    override fun mapFromCached(cached: CachedArticle): ArticleEntity {
        return ArticleEntity(
            title = cached.title,
            link = cached.link,
            pubDate = cached.pubDate,
            description = cached.description,
            image = cached.image,
            read = cached.read,
            feedUrl = cached.feedUrl,
            author = cached.author,
            category = cached.category,
            channel = cached.channel,
            copyright = cached.copyright,
            generator = cached.generator,
            guid = cached.guid,
            lastBuildDate = cached.lastBuildDate,
            managingEditor = cached.managingEditor,
            ttl = cached.ttl
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
            feedUrl = entity.feedUrl,
            author = entity.author,
            category = entity.category,
            channel = entity.channel,
            copyright = entity.copyright,
            generator = entity.generator,
            guid = entity.guid,
            lastBuildDate = entity.lastBuildDate,
            managingEditor = entity.managingEditor,
            ttl = entity.ttl
        )
    }
}