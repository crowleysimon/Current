package com.crowleysimon.cache.mapper

import com.crowleysimon.cache.model.CachedArticle
import com.crowleysimon.data.model.Article
import java.util.*

class CachedArticleMapper() : CacheMapper<CachedArticle, Article> {

    override fun mapFromCached(cached: CachedArticle): Article {
        return Article(
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
            ttl = cached.ttl,
            feedTitle = cached.feedTitle
        )
    }

    override fun mapToCached(entity: Article): CachedArticle {
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
            guid = entity.guid ?: UUID.randomUUID().toString(),
            lastBuildDate = entity.lastBuildDate,
            managingEditor = entity.managingEditor,
            ttl = entity.ttl,
            feedTitle = entity.feedTitle
        )
    }
}